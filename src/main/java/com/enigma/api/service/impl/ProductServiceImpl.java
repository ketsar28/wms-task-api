package com.enigma.api.service.impl;

import com.enigma.api.entity.Branch;
import com.enigma.api.entity.Product;
import com.enigma.api.model.request.product.ProductRequest;
import com.enigma.api.model.request.product.SearchProductRequest;
import com.enigma.api.model.response.branch.BranchResponse;
import com.enigma.api.model.response.product.ProductResponse;
import com.enigma.api.repository.BranchRepository;
import com.enigma.api.repository.ProductRepository;
import com.enigma.api.service.interfaces.BranchService;
import com.enigma.api.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import javax.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;
    private final BranchService branchService;

    private static void saveToDb(ProductRequest request, Product product, BranchResponse isBranchExists) {
        product.setPrice(request.getPrice());
        product.setProductId(request.getProductId());
        product.setProductCode(request.getProductCode());
        product.setProductName(request.getProductName());
        product.setProductPriceId(UUID.randomUUID().toString());
        product.setBranch(Branch.builder()
                        .branchId(isBranchExists.getBranchId())
                        .branchCode(isBranchExists.getBranchCode())
                        .address(isBranchExists.getAddress())
                        .branchName(isBranchExists.getBranchName())
                        .phoneNumber(isBranchExists.getPhoneNumber())
                .build());
    }

    private static ProductResponse toProductResponse(Product product, Boolean isIncludeProductPrice) {
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

    private static Branch toBranch(BranchResponse branch) {
        return Branch.builder()
                .branchId(branch.getBranchId())
                .branchName(branch.getBranchName())
                .address(branch.getAddress())
                .branchCode(branch.getBranchCode())
                .phoneNumber(branch.getPhoneNumber())
                .build();
    }

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        BranchResponse isBranchExists = branchService.getBranchById(request.getBranchId());
        Product product = new Product();

        if(Objects.nonNull(isBranchExists)) {
            saveToDb(request, product, isBranchExists);
            productRepository.saveAndFlush(product);
        }

        return ProductResponse.builder()
                .productId(product.getProductId())
                .productCode(product.getProductCode())
                .productName(product.getProductName())
                .productPriceId(product.getProductPriceId())
                .price(product.getPrice())
                .branch(isBranchExists)
                .build();
    }

    @Override
    public ProductResponse getProductById(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "products is not found"));

        return toProductResponse(product, true);
    }

    @Override
    public Page<ProductResponse> getAllProducts(SearchProductRequest request) {
        Specification<Product> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(Objects.nonNull(request.getProductCode())) {
                predicates.add(builder.like(root.get("productCode"), "%" + request.getProductCode() + "%"));
            }

            if(Objects.nonNull(request.getProductName())) {
                predicates.add(builder.like(root.get("productName"), "%" + request.getProductName() + "%"));
            }

            if(Objects.nonNull(request.getMinPrice())) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("price"), request.getMinPrice()));
            }


            if(Objects.nonNull(request.getMaxPrice())) {
                predicates.add(builder.lessThanOrEqualTo(root.get("price"), request.getMaxPrice()));
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by("lastModifiedDate").descending());
        Page<Product> products = productRepository.findAll(specification, pageable);

        List<ProductResponse> productResponses = products.getContent().stream()
                .map((Product product) -> toProductResponse(product, true))
                .collect(Collectors.toList());

        return new PageImpl<>(productResponses, pageable, products.getTotalElements());
    }

    @Override
    public Page<ProductResponse> getAllProductsByBranchId(String branchId) {
        Branch branch = branchRepository.findFirstByBranchId(branchId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "branch is not found"));
        Specification<Product> specification = (root, query, builder) -> builder.equal(root.get("branch"), branch);

        Pageable pageable = PageRequest.of(0, 5);
        Page<Product> products = productRepository.findAll(specification, pageable);

        List<ProductResponse> productResponses = products.getContent().stream().map(product -> toProductResponse(product, true)).collect(Collectors.toList());

        return new PageImpl<>(productResponses, pageable, products.getTotalElements());
    }

    @Override
    public ProductResponse updateProduct(ProductRequest request) {
        BranchResponse branch = branchService.getBranchById(request.getBranchId());

        if(Objects.nonNull(branch)) {
            Product product = productRepository.findByProductId(request.getProductId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product is not found"));

            product.setBranch(toBranch(branch));
            product.setPrice(request.getPrice());
            product.setProductName(request.getProductName());
            product.setProductCode(request.getProductCode());
            productRepository.save(product);

            return toProductResponse(product, true);
        }

        return null;
    }

    @Override
    public void deleteProduct(String productId) {
        productRepository.deleteById(productId);
    }
}
