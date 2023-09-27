package com.enigma.api.service.impl;

import com.enigma.api.entity.*;
import com.enigma.api.entity.enums.TransEnum;
import com.enigma.api.model.request.orderdetail.OrderDetailRequest;
import com.enigma.api.model.request.transaction.SearchTransactionRequest;
import com.enigma.api.model.request.transaction.request.CreateTransactionRequest;
import com.enigma.api.model.response.branch.BranchResponse;
import com.enigma.api.model.response.orderdetail.OrderDetailResponse;
import com.enigma.api.model.response.product.ProductResponse;
import com.enigma.api.model.response.transaction.TransactionResponse;
import com.enigma.api.repository.OrderDetailRepository;
import com.enigma.api.repository.ProductRepository;
import com.enigma.api.repository.ReceiptNumberRepository;
import com.enigma.api.repository.TransactionRepository;
import com.enigma.api.service.interfaces.ProductService;
import com.enigma.api.service.interfaces.TransactionService;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final OrderDetailRepository orderDetailRepository;
    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;
    private final ReceiptNumberRepository receiptNumberRepository;
    private final ProductServiceImpl productService;

    public TransactionResponse toResponseTransaction(Transaction transaction, List<OrderDetailResponse> billDetail) {
        return TransactionResponse.builder()
                .billId(transaction.getBillId())
                .receiptNumber(transaction.getReceiptNumber())
                .transDate(new Date())
                .transactionType(String.valueOf(transaction.getTransactionType()))
                .billDetails(billDetail)
                .build();
    }

    private ProductResponse toProductResponse(Product product, Boolean isIncludeProductPrice) {
        ProductResponse.ProductResponseBuilder products = ProductResponse.builder()
                .productId(product.getProductId())
                .productCode(product.getProductCode())
                .productName(product.getProductName())
                .price(product.getPrice())
                .branch(BranchResponse.builder()
                        .branchId(product.getBranch().getBranchId())
                        .branchCode(product.getBranch().getBranchCode())
                        .branchName(product.getBranch().getBranchName())
                        .address(product.getBranch().getAddress())
                        .phoneNumber(product.getBranch().getPhoneNumber())
                        .build());

        if(isIncludeProductPrice) {
            products.productPriceId(product.getProductPriceId());
        }

        return products.build();
    }
    public OrderDetailResponse toResponseBillDetail(OrderDetail orderDetail, Boolean isIncludeProductPriceId, Boolean isIncludeTotalSales) {
        OrderDetailResponse.OrderDetailResponseBuilder builder = OrderDetailResponse.builder()
                .billDetailId(orderDetail.getBillDetailId())
                .billId(orderDetail.getBill().getBillId())
                .product(toProductResponse(orderDetail.getProduct(), isIncludeProductPriceId))
                .quantity(orderDetail.getQuantity());


        if (isIncludeTotalSales) {
            builder.totalSales(orderDetail.getTotalSales());
        }

        return builder.build();
    }

    public String generateReceiptNumber(String branchCode) {
        Integer year = LocalDateTime.now().getYear();
        ReceiptNumber sequence = receiptNumberRepository.findByBranchCodeAndYear(branchCode, year);
        if (Objects.isNull(sequence)) {
            sequence = new ReceiptNumber();
            sequence.setBranchCode(branchCode);
            sequence.setYear(year);
            sequence.setCurrentValue(1);
        } else {
            sequence.setCurrentValue(sequence.getCurrentValue() + 1);
        }

        String formatReceiptNumber = String.format("%s-%d-%03d", branchCode, year, sequence.getCurrentValue());

        receiptNumberRepository.saveAndFlush(sequence);

        return formatReceiptNumber;
    }



    @Transactional(rollbackOn = Exception.class)
    @Override
    public TransactionResponse createTransaction(CreateTransactionRequest request) {
        TransEnum transactionTypeEnum = TransEnum.fromValue(request.getTransactionType());
        Transaction transaction = null;

        List<OrderDetailResponse> orderDetailList = new ArrayList<>();

        for (OrderDetailRequest orderDetailRequest : request.getBillDetails()) {
            String productPriceId = orderDetailRequest.getProductPriceId();
            Product product = productRepository.findByProductPriceId(productPriceId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product price id not found"));

            String receiptNumber = generateReceiptNumber(product.getBranch().getBranchCode());
            transaction = new Transaction();
            transaction.setTransactionType(transactionTypeEnum);
            transaction.setBillId(request.getBillId());
            transaction.setTransDate(LocalDateTime.now());
            transaction.setReceiptNumber(receiptNumber);
            transactionRepository.saveAndFlush(transaction);

            BigDecimal totalSales = product.getPrice().multiply(BigDecimal.valueOf(orderDetailRequest.getQuantity()));

            OrderDetail billDetail = new OrderDetail();
            billDetail.setBillDetailId(orderDetailRequest.getBillDetailId());
            billDetail.setProduct(product);
            billDetail.setTotalSales(totalSales);
            billDetail.setQuantity(orderDetailRequest.getQuantity());
            billDetail.setBill(transaction);
            orderDetailRepository.saveAndFlush(billDetail);

            OrderDetailResponse detailResponse = toResponseBillDetail(billDetail,  true, true);
            orderDetailList.add(detailResponse);
        }
        return toResponseTransaction(transaction, orderDetailList);
    }

    @Transactional(rollbackOn = Exception.class)
    public Page<TransactionResponse> getTransactionsList(SearchTransactionRequest transactionRequest) {
        Specification<Transaction> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (transactionRequest.getReceiptNumber() != null) {
                predicates.add(builder.like(root.get("receiptNumber"), "%" + transactionRequest.getReceiptNumber() + "%"));
            }

            if (transactionRequest.getTransType() != null) {
                try {
                    TransEnum transType = TransEnum.fromValue(transactionRequest.getTransType());
                    predicates.add(builder.equal(root.get("transactionType"), transType));
                } catch (IllegalArgumentException e) {
                    System.out.println("you receive this errors, because : " + e);
                }
            }

            if (transactionRequest.getProductName() != null) {
                Join<Transaction, OrderDetail> billDetails = root.join("billDetails");
                Join<OrderDetail, Product> product = billDetails.join("product");
                predicates.add(builder.like(product.get("productName"), "%" + transactionRequest.getProductName() + "%"));
            }


            return query.where(predicates.toArray(predicates.toArray(new Predicate[]{}))).getRestriction();
        };

        Pageable pageable = PageRequest.of(transactionRequest.getPage(), transactionRequest.getSize(), Sort.by("lastModifiedDate").descending());
        Page<Transaction> transactions = transactionRepository.findAll(specification, pageable);

        List<TransactionResponse> transactionResponses = transactions.getContent().stream()
                .map(transaction -> toResponseTransaction(transaction,
                        transaction.getBillDetails().stream()
                                .map(billDetail -> toResponseBillDetail(billDetail,false, false))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        return new PageImpl<>(transactionResponses, pageable, transactions.getTotalElements());
    }

    @Override
    public TransactionResponse getTransaction(String orderId) {
        List<OrderDetail> orderDetails = orderDetailRepository.findAllByBill_BillId(orderId);

        if (orderDetails.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction is Not Found");
        }
        Transaction transaction = orderDetails.get(0).getBill();
        List<OrderDetailResponse> orderDetailsList = new ArrayList<>();
        for (OrderDetail billDetail : orderDetails) {
            OrderDetailResponse billDetailResponse = toResponseBillDetail(billDetail,false, false);
            orderDetailsList.add(billDetailResponse);
        }
        return toResponseTransaction(transaction, orderDetailsList);
    }

    @Override
    public Page<TransactionResponse> getTransactionList(SearchTransactionRequest request) {
        return null;
    }

    @Override
    public Map<String, BigDecimal> getAllSales() {
        HashMap<String, BigDecimal> totalSales = new HashMap<>();

        BigDecimal eatInTotal = BigDecimal.ZERO;
        BigDecimal onlineTotal = BigDecimal.ZERO;
        BigDecimal takeAwayTotal = BigDecimal.ZERO;
        List<Transaction> transactions = transactionRepository.findAll();;

        for (Transaction transaction : transactions) {
            if (transaction.getTransactionType() == TransEnum.EAT_IN) {
                eatInTotal = eatInTotal.add(calculateTotalSales(transaction));
            } else if (transaction.getTransactionType() == TransEnum.ONLINE) {
                onlineTotal = onlineTotal.add(calculateTotalSales(transaction));
            } else if (transaction.getTransactionType() == TransEnum.TAKE_AWAY) {
                takeAwayTotal = takeAwayTotal.add(calculateTotalSales(transaction));
            }
        }

        totalSales.put("eatIn", eatInTotal);
        totalSales.put("takeAway", takeAwayTotal);
        totalSales.put("online", onlineTotal);

        return totalSales;

    }

    public BigDecimal calculateTotalSales(Transaction transaction) {
        BigDecimal totalSales = BigDecimal.ZERO;

        for (OrderDetail orderDetail : transaction.getBillDetails()) {
            totalSales = totalSales.add(orderDetail.getTotalSales());
        }

        return totalSales;
    }
}
