//package com.enigma.api.controller;
//
//import com.enigma.api.entity.Branch;
//import com.enigma.api.entity.Product;
//import com.enigma.api.repository.BranchRepository;
//import com.enigma.api.repository.ProductRepository;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.time.LocalDateTime;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class TransactionControllerTest {
//
//    private static String branchId;
//    private static String productPriceId;
//    private static String productPriceId2;
//    private static String billId;
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    private ProductRepository productRepository;
//    @Autowired
//    private BranchRepository branchRepository;
//    @Autowired
//    private BillDetailRepository billDetailRepository;
//    @Autowired
//    private SequenceNumberRepository sequenceNumberRepository;
//    @Autowired
//    private TransactionRepository transactionRepository;
//
//    @BeforeEach
//    void setUp() {
//        billDetailRepository.deleteAll();
//        sequenceNumberRepository.deleteAll();
//        transactionRepository.deleteAll();
//        productRepository.deleteAll();
//        branchRepository.deleteAll();
//    }
//
//
//    @Order(1)
//    @Test
//    public void testCreateBranch() throws Exception {
//        Map<String, Object> request = new HashMap<>();
//        request.put("branchName", "Cilandak");
//        request.put("branchCode", "0205");
//        request.put("address", "Jl. Cilandak Raya No.XX");
//        request.put("phoneNumber", "08123441234");
//
//        mockMvc.perform(post("/api/branch")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsString(request))
//                ).andExpectAll(status().isCreated())
//                .andDo(result -> {
//                    String jsonString = result.getResponse().getContentAsString();
//                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
//                    });
//
//                    Map<String, Object> data = (Map<String, Object>) mapResponse.get("data");
//                    branchId = data.get("branchId").toString();
//
//                    System.out.println("data : " + data);
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                });
//    }
//
//    @Order(2)
//    @Test
//    public void testCreateProduct() throws Exception {
//        Branch branch = new Branch();
//        branch.setBranchId(UUID.randomUUID().toString());
//        branch.setBranchName("Cilandak");
//        branch.setBranchCode("0205");
//        branch.setAddress("Jl. Cilandak Raya No.XX");
//        branch.setPhoneNumber("08123441234");
//        branchRepository.save(branch);
//
//        branchId = branch.getBranchId();
//
//        Map<String, Object> request = new HashMap<>();
//        request.put("productCode", "01-001");
//        request.put("productName", "Nasi Putih");
//        request.put("price", 5000);
//        request.put("branchId", branchId);
//
//        mockMvc.perform(post("/api/products")
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpectAll(status().isCreated())
//                .andDo(result -> {
//                    String jsonString = result.getResponse().getContentAsString();
//                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
//                    });
//                    Map<String, Object> data = (Map<String, Object>) mapResponse.get("data");
//
//                    productPriceId = data.get("productPriceId").toString();
//
//                    System.out.println("data (product 1) : " + data);
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                    System.out.println();
//                });
//
//        Map<String, Object> request2 = new HashMap<>();
//        request2.put("productCode", "01-002");
//        request2.put("productName", "Aneka Tumisan");
//        request2.put("price", 2000);
//        request2.put("branchId", branchId);
//
//        mockMvc.perform(post("/api/products")
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsString(request2)))
//                .andExpectAll(status().isCreated())
//                .andDo(result -> {
//                    String jsonString = result.getResponse().getContentAsString();
//                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
//                    });
//                    Map<String, Object> data = (Map<String, Object>) mapResponse.get("data");
//
//                    productPriceId2 = data.get("productPriceId").toString();
//
//                    System.out.println("data (product 2) : " + data);
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                    System.out.println();
//                });
//
//    }
//
//    @Order(3)
//    @Test
//    public void testCreateTransaction() throws Exception {
//        Branch branch = new Branch();
//        branch.setBranchId(UUID.randomUUID().toString());
//        branch.setBranchName("Cilandak");
//        branch.setBranchCode("0205");
//        branch.setAddress("Jl. Cilandak Raya No.XX");
//        branch.setPhoneNumber("08123441234");
//        branchRepository.save(branch);
//
//        branchId = branch.getBranchId();
//
//        Product product = new Product();
//        product.setProductId(UUID.randomUUID().toString());
//        product.setProductPriceId(UUID.randomUUID().toString());
//        product.setProductCode("01-001");
//        product.setProductName("Nasi Putih");
//        product.setPrice(new BigDecimal(BigInteger.valueOf(5000)));
//        product.setBranch(branch);
//        productRepository.save(product);
//        productPriceId = product.getProductPriceId();
//
//        Product product2 = new Product();
//        product2.setProductId(UUID.randomUUID().toString());
//        product2.setProductPriceId(UUID.randomUUID().toString());
//        product2.setProductCode("01-002");
//        product2.setProductName("Aneka Tumisa");
//        product2.setPrice(new BigDecimal(BigInteger.valueOf(2000)));
//        product2.setBranch(branch);
//        productRepository.save(product2);
//        productPriceId2 = product2.getProductPriceId();
//
//        Map<String, Object> request = new HashMap<>();
//        request.put("transactionType", "1");
//
//        Map<String, Object> billDetailRequest1 = new HashMap<>();
//        billDetailRequest1.put("productPriceId", productPriceId);
//        billDetailRequest1.put("quantity", 1);
//
//        Map<String, Object> billDetailRequest2 = new HashMap<>();
//        billDetailRequest2.put("productPriceId", productPriceId2);
//        billDetailRequest2.put("quantity", 1);
//
//        List<Map<String, Object>> billDetails = List.of(billDetailRequest1, billDetailRequest2);
//        request.put("billDetails", billDetails);
//
//        mockMvc.perform(post("/api/transactions")
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpectAll(status().isCreated())
//                .andDo(result -> {
//                    String jsonString = result.getResponse().getContentAsString();
//                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
//                    });
//                    Map<String, Object> data = (Map<String, Object>) mapResponse.get("data");
//
//                    assertNotNull(data.get("billId"));
//                    assertNotNull(data.get("receiptNumber"));
//                    assertEquals("EAT_IN", data.get("transactionType"));
//                    assertNotNull(data.get("transDate"));
//
//                    assertInstanceOf(List.class, data.get("billDetails"));
//                    List<Map<String, Object>> billDetailsResponses = (List<Map<String, Object>>) data.get("billDetails");
//                    assertEquals(2, billDetailsResponses.size());
//                    billId = data.get("billId").toString();
//
//                    System.out.println("data : " + data);
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                    System.out.println();
//                });
//    }
//
//    @Test
//    @Order(4)
//    public void testGetTransactionById() throws Exception {
//        Branch branch = new Branch();
//        branch.setBranchId(UUID.randomUUID().toString());
//        branch.setBranchName("Cilandak");
//        branch.setBranchCode("0205");
//        branch.setAddress("Jl. Cilandak Raya No.XX");
//        branch.setPhoneNumber("08123441234");
//        branchRepository.save(branch);
//
//        branchId = branch.getBranchId();
//
//        Product product = new Product();
//        product.setProductId(UUID.randomUUID().toString());
//        product.setProductPriceId(UUID.randomUUID().toString());
//        product.setProductCode("01-001");
//        product.setProductName("Nasi Putih");
//        product.setPrice(new BigDecimal(BigInteger.valueOf(5000)));
//        product.setBranch(branch);
//        productRepository.save(product);
//        productPriceId = product.getProductPriceId();
//
//        Product product2 = new Product();
//        product2.setProductId(UUID.randomUUID().toString());
//        product2.setProductPriceId(UUID.randomUUID().toString());
//        product2.setProductCode("01-002");
//        product2.setProductName("Aneka Tumisa");
//        product2.setPrice(new BigDecimal(BigInteger.valueOf(2000)));
//        product2.setBranch(branch);
//        productRepository.save(product2);
//        productPriceId2 = product2.getProductPriceId();
//
//        List<BillDetail> billDetails = new ArrayList<>();
//
//        Transaction transaction = new Transaction();
//        transaction.setTransDate(LocalDateTime.now());
//        transaction.setBillId(UUID.randomUUID().toString());
//        transaction.setTransactionType(TransactionTypeEnum.EAT_IN);
//
//        String receiptNumber = generateReceiptNumber(branch.getBranchCode());
//        transaction.setReceiptNumber(receiptNumber);
//        transactionRepository.save(transaction);
//
//        BillDetail billDetail = new BillDetail();
//        billDetail.setBill(transaction);
//        billDetail.setProduct(product);
//        billDetail.setQuantity(1);
//        billDetail.setBillDetailId(UUID.randomUUID().toString());
//        billDetail.setTotalSales(product.getPrice().multiply(BigDecimal.valueOf(2)));
//        billDetailRepository.save(billDetail);
//        billDetails.add(billDetail);
//
//        billId = billDetail.getBill().getBillId();
//
//        mockMvc.perform(get("/api/transactions/" + billId)
//                        .accept(MediaType.APPLICATION_JSON_VALUE))
//                .andExpectAll(status().isOk())
//                .andDo(result -> {
//                    String jsonString = result.getResponse().getContentAsString();
//                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
//                    });
//                    Map<String, Object> data = (Map<String, Object>) mapResponse.get("data");
//
//                    assertNotNull(data.get("billId"));
//                    assertNotNull(data.get("receiptNumber"));
//                    assertEquals("EAT_IN", data.get("transactionType"));
//                    assertNotNull(data.get("transDate"));
//                    assertInstanceOf(List.class, data.get("billDetails"));
//
//                    System.out.println("data : " + data);
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                    System.out.println();
//                });
//    }
//
//    @Order(5)
//    @Test
//    public void testGetTransactionByIdFailedInvalidId() throws Exception {
//        mockMvc.perform(get("/api/transactions/xxx")
//                        .accept(MediaType.APPLICATION_JSON_VALUE))
//                .andExpectAll(status().isNotFound())
//                .andDo(result -> {
//                    String jsonString = result.getResponse().getContentAsString();
//                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
//                    });
//
//                    assertNotNull(mapResponse.get("errors"));
//                    assertNull(mapResponse.get("data"));
//
//                    System.out.println("data : " + mapResponse.get("data"));
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                    System.out.println();
//                });
//    }
//
//    @Order(6)
//    @Test
//    public void testGetAllTransaction() throws Exception {
//        List<BillDetail> billDetails = new ArrayList<>();
//
//        Branch branch = new Branch();
//        branch.setBranchId(UUID.randomUUID().toString());
//        branch.setBranchName("Cilandak");
//        branch.setBranchCode("0205");
//        branch.setAddress("Jl. Cilandak Raya No.XX");
//        branch.setPhoneNumber("08123441234");
//        branchRepository.save(branch);
//
//        branchId = branch.getBranchId();
//
//        Product product = new Product();
//        product.setProductId(UUID.randomUUID().toString());
//        product.setProductPriceId(UUID.randomUUID().toString());
//        product.setProductCode("01-001");
//        product.setProductName("Nasi Putih");
//        product.setPrice(new BigDecimal(BigInteger.valueOf(5000)));
//        product.setBranch(branch);
//        productRepository.save(product);
//        productPriceId = product.getProductPriceId();
//
//        Product product2 = new Product();
//        product2.setProductId(UUID.randomUUID().toString());
//        product2.setProductPriceId(UUID.randomUUID().toString());
//        product2.setProductCode("01-002");
//        product2.setProductName("Aneka Tumisa");
//        product2.setPrice(new BigDecimal(BigInteger.valueOf(2000)));
//        product2.setBranch(branch);
//        productRepository.save(product2);
//        productPriceId2 = product2.getProductPriceId();
//
//        for (int i = 0; i < 2; i++) {
//            Transaction transaction = new Transaction();
//            transaction.setTransDate(LocalDateTime.now());
//            transaction.setBillId(UUID.randomUUID().toString());
//            transaction.setTransactionType(TransactionTypeEnum.EAT_IN);
//
//            String receiptNumber = generateReceiptNumber(branch.getBranchCode());
//            transaction.setReceiptNumber(receiptNumber);
//            transactionRepository.save(transaction);
//
//            for (int j = 0; j < 2; j++) {
//                BillDetail billDetail = new BillDetail();
//                billDetail.setBill(transaction);
//                billDetail.setProduct(product);
//                billDetail.setQuantity(1);
//                billDetail.setBillDetailId(UUID.randomUUID() + "-" + i);
//                billDetail.setTotalSales(product.getPrice().multiply(BigDecimal.valueOf(2)));
//                billDetailRepository.save(billDetail);
//                billDetails.add(billDetail);
//
//                billId = billDetail.getBill().getBillId();
//            }
//        }
//
//
//        mockMvc.perform(get("/api/transactions")
//                        .param("page", "0")
//                        .param("size", "10")
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                ).andExpectAll(status().isOk())
//                .andDo(result -> {
//                    String jsonString = result.getResponse().getContentAsString();
//                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
//                    });
//
//                    assertNull(mapResponse.get("errors"));
//                    assertInstanceOf(List.class, mapResponse.get("data"));
//                    assertNotNull(mapResponse.get("paging"));
//
//                    System.out.println("data : " + mapResponse.get("data"));
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                    System.out.println("paging : " + mapResponse.get("paging"));
//                    System.out.println();
//                });
//    }
//
//    @Order(7)
//    @Test
//    public void testGetTotalSales() throws Exception {
//        List<BillDetail> billDetails = new ArrayList<>();
//
//        Branch branch = new Branch();
//        branch.setBranchId(UUID.randomUUID().toString());
//        branch.setBranchName("Cilandak");
//        branch.setBranchCode("0205");
//        branch.setAddress("Jl. Cilandak Raya No.XX");
//        branch.setPhoneNumber("08123441234");
//        branchRepository.save(branch);
//        branchId = branch.getBranchId();
//
//        List<ProductInfo> productInfos = new ArrayList<>();
//        productInfos.add(new ProductInfo("01-001", "Nasi Putih", new BigDecimal("5000")));
//        productInfos.add(new ProductInfo("01-002", "Aneka Tumisa", new BigDecimal("2000")));
//        productInfos.add(new ProductInfo("01-003", "Semur Jengkol", new BigDecimal("7000")));
//
//        for (int i = 0; i < 3; i++) {
//            Transaction transaction = new Transaction();
//            transaction.setTransDate(LocalDateTime.now());
//            transaction.setBillId(UUID.randomUUID().toString());
//
//            switch (i) {
//                case 0 -> transaction.setTransactionType(TransactionTypeEnum.EAT_IN);
//                case 1 -> transaction.setTransactionType(TransactionTypeEnum.ONLINE);
//                case 2 -> transaction.setTransactionType(TransactionTypeEnum.TAKE_AWAY);
//            }
//
//            String receiptNumber = generateReceiptNumber(branch.getBranchCode());
//            transaction.setReceiptNumber(receiptNumber);
//            transactionRepository.save(transaction);
//
//            for (ProductInfo productInfo : productInfos) {
//                Product product = new Product();
//                product.setProductId(UUID.randomUUID().toString());
//                product.setProductPriceId(UUID.randomUUID().toString());
//                product.setProductCode(productInfo.getProductCode());
//                product.setProductName(productInfo.getProductName());
//                product.setPrice(productInfo.getPrice());
//                product.setBranch(branch);
//                productRepository.save(product);
//                productPriceId = product.getProductPriceId();
//
//                BillDetail billDetail = new BillDetail();
//                billDetail.setBill(transaction);
//                billDetail.setProduct(product);
//                billDetail.setQuantity(1);
//                billDetail.setBillDetailId(UUID.randomUUID().toString());
//                billDetail.setTotalSales(product.getPrice().multiply(BigDecimal.valueOf(2)));
//                billDetailRepository.save(billDetail);
//                billDetails.add(billDetail);
//            }
//        }
//
//
//        mockMvc.perform(get("/api/transactions/total-sales")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpectAll(status().isOk())
//                .andDo(result -> {
//                    String jsonString = result.getResponse().getContentAsString();
//                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
//                    });
//
//                    assertNull(mapResponse.get("errors"));
//                    assertInstanceOf(Map.class, mapResponse.get("data"));
//
//                    System.out.println("data : " + mapResponse.get("data"));
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                    System.out.println("paging : " + mapResponse.get("paging"));
//                    System.out.println();
//                });
//    }
//
//    public String generateReceiptNumber(String branchCode) {
//        Integer year = LocalDateTime.now().getYear();
//        SequenceNumber sequence = sequenceNumberRepository.findByBranchCodeAndYear(branchCode, year);
//        if (Objects.isNull(sequence)) {
//            sequence = new SequenceNumber();
//            sequence.setBranchCode(branchCode);
//            sequence.setYear(year);
//            sequence.setCurrentValue(1);
//        } else {
//            sequence.setCurrentValue(sequence.getCurrentValue() + 1);
//        }
//
//        String formatReceiptNumber = String.format("%s-%d-%03d", branchCode, year, sequence.getCurrentValue());
//        sequenceNumberRepository.save(sequence);
//
//        return formatReceiptNumber;
//    }
//}package com.enigma.api.controller;
//
//import com.enigma.api.entity.Branch;
//import com.enigma.api.entity.Product;
//import com.enigma.api.repository.BranchRepository;
//import com.enigma.api.repository.ProductRepository;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.time.LocalDateTime;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class TransactionControllerTest {
//
//    private static String branchId;
//    private static String productPriceId;
//    private static String productPriceId2;
//    private static String billId;
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    private ProductRepository productRepository;
//    @Autowired
//    private BranchRepository branchRepository;
//    @Autowired
//    private BillDetailRepository billDetailRepository;
//    @Autowired
//    private SequenceNumberRepository sequenceNumberRepository;
//    @Autowired
//    private TransactionRepository transactionRepository;
//
//    @BeforeEach
//    void setUp() {
//        billDetailRepository.deleteAll();
//        sequenceNumberRepository.deleteAll();
//        transactionRepository.deleteAll();
//        productRepository.deleteAll();
//        branchRepository.deleteAll();
//    }
//
//
//    @Order(1)
//    @Test
//    public void testCreateBranch() throws Exception {
//        Map<String, Object> request = new HashMap<>();
//        request.put("branchName", "Cilandak");
//        request.put("branchCode", "0205");
//        request.put("address", "Jl. Cilandak Raya No.XX");
//        request.put("phoneNumber", "08123441234");
//
//        mockMvc.perform(post("/api/branch")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsString(request))
//                ).andExpectAll(status().isCreated())
//                .andDo(result -> {
//                    String jsonString = result.getResponse().getContentAsString();
//                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
//                    });
//
//                    Map<String, Object> data = (Map<String, Object>) mapResponse.get("data");
//                    branchId = data.get("branchId").toString();
//
//                    System.out.println("data : " + data);
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                });
//    }
//
//    @Order(2)
//    @Test
//    public void testCreateProduct() throws Exception {
//        Branch branch = new Branch();
//        branch.setBranchId(UUID.randomUUID().toString());
//        branch.setBranchName("Cilandak");
//        branch.setBranchCode("0205");
//        branch.setAddress("Jl. Cilandak Raya No.XX");
//        branch.setPhoneNumber("08123441234");
//        branchRepository.save(branch);
//
//        branchId = branch.getBranchId();
//
//        Map<String, Object> request = new HashMap<>();
//        request.put("productCode", "01-001");
//        request.put("productName", "Nasi Putih");
//        request.put("price", 5000);
//        request.put("branchId", branchId);
//
//        mockMvc.perform(post("/api/products")
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpectAll(status().isCreated())
//                .andDo(result -> {
//                    String jsonString = result.getResponse().getContentAsString();
//                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
//                    });
//                    Map<String, Object> data = (Map<String, Object>) mapResponse.get("data");
//
//                    productPriceId = data.get("productPriceId").toString();
//
//                    System.out.println("data (product 1) : " + data);
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                    System.out.println();
//                });
//
//        Map<String, Object> request2 = new HashMap<>();
//        request2.put("productCode", "01-002");
//        request2.put("productName", "Aneka Tumisan");
//        request2.put("price", 2000);
//        request2.put("branchId", branchId);
//
//        mockMvc.perform(post("/api/products")
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsString(request2)))
//                .andExpectAll(status().isCreated())
//                .andDo(result -> {
//                    String jsonString = result.getResponse().getContentAsString();
//                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
//                    });
//                    Map<String, Object> data = (Map<String, Object>) mapResponse.get("data");
//
//                    productPriceId2 = data.get("productPriceId").toString();
//
//                    System.out.println("data (product 2) : " + data);
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                    System.out.println();
//                });
//
//    }
//
//    @Order(3)
//    @Test
//    public void testCreateTransaction() throws Exception {
//        Branch branch = new Branch();
//        branch.setBranchId(UUID.randomUUID().toString());
//        branch.setBranchName("Cilandak");
//        branch.setBranchCode("0205");
//        branch.setAddress("Jl. Cilandak Raya No.XX");
//        branch.setPhoneNumber("08123441234");
//        branchRepository.save(branch);
//
//        branchId = branch.getBranchId();
//
//        Product product = new Product();
//        product.setProductId(UUID.randomUUID().toString());
//        product.setProductPriceId(UUID.randomUUID().toString());
//        product.setProductCode("01-001");
//        product.setProductName("Nasi Putih");
//        product.setPrice(new BigDecimal(BigInteger.valueOf(5000)));
//        product.setBranch(branch);
//        productRepository.save(product);
//        productPriceId = product.getProductPriceId();
//
//        Product product2 = new Product();
//        product2.setProductId(UUID.randomUUID().toString());
//        product2.setProductPriceId(UUID.randomUUID().toString());
//        product2.setProductCode("01-002");
//        product2.setProductName("Aneka Tumisa");
//        product2.setPrice(new BigDecimal(BigInteger.valueOf(2000)));
//        product2.setBranch(branch);
//        productRepository.save(product2);
//        productPriceId2 = product2.getProductPriceId();
//
//        Map<String, Object> request = new HashMap<>();
//        request.put("transactionType", "1");
//
//        Map<String, Object> billDetailRequest1 = new HashMap<>();
//        billDetailRequest1.put("productPriceId", productPriceId);
//        billDetailRequest1.put("quantity", 1);
//
//        Map<String, Object> billDetailRequest2 = new HashMap<>();
//        billDetailRequest2.put("productPriceId", productPriceId2);
//        billDetailRequest2.put("quantity", 1);
//
//        List<Map<String, Object>> billDetails = List.of(billDetailRequest1, billDetailRequest2);
//        request.put("billDetails", billDetails);
//
//        mockMvc.perform(post("/api/transactions")
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpectAll(status().isCreated())
//                .andDo(result -> {
//                    String jsonString = result.getResponse().getContentAsString();
//                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
//                    });
//                    Map<String, Object> data = (Map<String, Object>) mapResponse.get("data");
//
//                    assertNotNull(data.get("billId"));
//                    assertNotNull(data.get("receiptNumber"));
//                    assertEquals("EAT_IN", data.get("transactionType"));
//                    assertNotNull(data.get("transDate"));
//
//                    assertInstanceOf(List.class, data.get("billDetails"));
//                    List<Map<String, Object>> billDetailsResponses = (List<Map<String, Object>>) data.get("billDetails");
//                    assertEquals(2, billDetailsResponses.size());
//                    billId = data.get("billId").toString();
//
//                    System.out.println("data : " + data);
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                    System.out.println();
//                });
//    }
//
//    @Test
//    @Order(4)
//    public void testGetTransactionById() throws Exception {
//        Branch branch = new Branch();
//        branch.setBranchId(UUID.randomUUID().toString());
//        branch.setBranchName("Cilandak");
//        branch.setBranchCode("0205");
//        branch.setAddress("Jl. Cilandak Raya No.XX");
//        branch.setPhoneNumber("08123441234");
//        branchRepository.save(branch);
//
//        branchId = branch.getBranchId();
//
//        Product product = new Product();
//        product.setProductId(UUID.randomUUID().toString());
//        product.setProductPriceId(UUID.randomUUID().toString());
//        product.setProductCode("01-001");
//        product.setProductName("Nasi Putih");
//        product.setPrice(new BigDecimal(BigInteger.valueOf(5000)));
//        product.setBranch(branch);
//        productRepository.save(product);
//        productPriceId = product.getProductPriceId();
//
//        Product product2 = new Product();
//        product2.setProductId(UUID.randomUUID().toString());
//        product2.setProductPriceId(UUID.randomUUID().toString());
//        product2.setProductCode("01-002");
//        product2.setProductName("Aneka Tumisa");
//        product2.setPrice(new BigDecimal(BigInteger.valueOf(2000)));
//        product2.setBranch(branch);
//        productRepository.save(product2);
//        productPriceId2 = product2.getProductPriceId();
//
//        List<BillDetail> billDetails = new ArrayList<>();
//
//        Transaction transaction = new Transaction();
//        transaction.setTransDate(LocalDateTime.now());
//        transaction.setBillId(UUID.randomUUID().toString());
//        transaction.setTransactionType(TransactionTypeEnum.EAT_IN);
//
//        String receiptNumber = generateReceiptNumber(branch.getBranchCode());
//        transaction.setReceiptNumber(receiptNumber);
//        transactionRepository.save(transaction);
//
//        BillDetail billDetail = new BillDetail();
//        billDetail.setBill(transaction);
//        billDetail.setProduct(product);
//        billDetail.setQuantity(1);
//        billDetail.setBillDetailId(UUID.randomUUID().toString());
//        billDetail.setTotalSales(product.getPrice().multiply(BigDecimal.valueOf(2)));
//        billDetailRepository.save(billDetail);
//        billDetails.add(billDetail);
//
//        billId = billDetail.getBill().getBillId();
//
//        mockMvc.perform(get("/api/transactions/" + billId)
//                        .accept(MediaType.APPLICATION_JSON_VALUE))
//                .andExpectAll(status().isOk())
//                .andDo(result -> {
//                    String jsonString = result.getResponse().getContentAsString();
//                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
//                    });
//                    Map<String, Object> data = (Map<String, Object>) mapResponse.get("data");
//
//                    assertNotNull(data.get("billId"));
//                    assertNotNull(data.get("receiptNumber"));
//                    assertEquals("EAT_IN", data.get("transactionType"));
//                    assertNotNull(data.get("transDate"));
//                    assertInstanceOf(List.class, data.get("billDetails"));
//
//                    System.out.println("data : " + data);
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                    System.out.println();
//                });
//    }
//
//    @Order(5)
//    @Test
//    public void testGetTransactionByIdFailedInvalidId() throws Exception {
//        mockMvc.perform(get("/api/transactions/xxx")
//                        .accept(MediaType.APPLICATION_JSON_VALUE))
//                .andExpectAll(status().isNotFound())
//                .andDo(result -> {
//                    String jsonString = result.getResponse().getContentAsString();
//                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
//                    });
//
//                    assertNotNull(mapResponse.get("errors"));
//                    assertNull(mapResponse.get("data"));
//
//                    System.out.println("data : " + mapResponse.get("data"));
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                    System.out.println();
//                });
//    }
//
//    @Order(6)
//    @Test
//    public void testGetAllTransaction() throws Exception {
//        List<BillDetail> billDetails = new ArrayList<>();
//
//        Branch branch = new Branch();
//        branch.setBranchId(UUID.randomUUID().toString());
//        branch.setBranchName("Cilandak");
//        branch.setBranchCode("0205");
//        branch.setAddress("Jl. Cilandak Raya No.XX");
//        branch.setPhoneNumber("08123441234");
//        branchRepository.save(branch);
//
//        branchId = branch.getBranchId();
//
//        Product product = new Product();
//        product.setProductId(UUID.randomUUID().toString());
//        product.setProductPriceId(UUID.randomUUID().toString());
//        product.setProductCode("01-001");
//        product.setProductName("Nasi Putih");
//        product.setPrice(new BigDecimal(BigInteger.valueOf(5000)));
//        product.setBranch(branch);
//        productRepository.save(product);
//        productPriceId = product.getProductPriceId();
//
//        Product product2 = new Product();
//        product2.setProductId(UUID.randomUUID().toString());
//        product2.setProductPriceId(UUID.randomUUID().toString());
//        product2.setProductCode("01-002");
//        product2.setProductName("Aneka Tumisa");
//        product2.setPrice(new BigDecimal(BigInteger.valueOf(2000)));
//        product2.setBranch(branch);
//        productRepository.save(product2);
//        productPriceId2 = product2.getProductPriceId();
//
//        for (int i = 0; i < 2; i++) {
//            Transaction transaction = new Transaction();
//            transaction.setTransDate(LocalDateTime.now());
//            transaction.setBillId(UUID.randomUUID().toString());
//            transaction.setTransactionType(TransactionTypeEnum.EAT_IN);
//
//            String receiptNumber = generateReceiptNumber(branch.getBranchCode());
//            transaction.setReceiptNumber(receiptNumber);
//            transactionRepository.save(transaction);
//
//            for (int j = 0; j < 2; j++) {
//                BillDetail billDetail = new BillDetail();
//                billDetail.setBill(transaction);
//                billDetail.setProduct(product);
//                billDetail.setQuantity(1);
//                billDetail.setBillDetailId(UUID.randomUUID() + "-" + i);
//                billDetail.setTotalSales(product.getPrice().multiply(BigDecimal.valueOf(2)));
//                billDetailRepository.save(billDetail);
//                billDetails.add(billDetail);
//
//                billId = billDetail.getBill().getBillId();
//            }
//        }
//
//
//        mockMvc.perform(get("/api/transactions")
//                        .param("page", "0")
//                        .param("size", "10")
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                ).andExpectAll(status().isOk())
//                .andDo(result -> {
//                    String jsonString = result.getResponse().getContentAsString();
//                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
//                    });
//
//                    assertNull(mapResponse.get("errors"));
//                    assertInstanceOf(List.class, mapResponse.get("data"));
//                    assertNotNull(mapResponse.get("paging"));
//
//                    System.out.println("data : " + mapResponse.get("data"));
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                    System.out.println("paging : " + mapResponse.get("paging"));
//                    System.out.println();
//                });
//    }
//
//    @Order(7)
//    @Test
//    public void testGetTotalSales() throws Exception {
//        List<BillDetail> billDetails = new ArrayList<>();
//
//        Branch branch = new Branch();
//        branch.setBranchId(UUID.randomUUID().toString());
//        branch.setBranchName("Cilandak");
//        branch.setBranchCode("0205");
//        branch.setAddress("Jl. Cilandak Raya No.XX");
//        branch.setPhoneNumber("08123441234");
//        branchRepository.save(branch);
//        branchId = branch.getBranchId();
//
//        List<ProductInfo> productInfos = new ArrayList<>();
//        productInfos.add(new ProductInfo("01-001", "Nasi Putih", new BigDecimal("5000")));
//        productInfos.add(new ProductInfo("01-002", "Aneka Tumisa", new BigDecimal("2000")));
//        productInfos.add(new ProductInfo("01-003", "Semur Jengkol", new BigDecimal("7000")));
//
//        for (int i = 0; i < 3; i++) {
//            Transaction transaction = new Transaction();
//            transaction.setTransDate(LocalDateTime.now());
//            transaction.setBillId(UUID.randomUUID().toString());
//
//            switch (i) {
//                case 0 -> transaction.setTransactionType(TransactionTypeEnum.EAT_IN);
//                case 1 -> transaction.setTransactionType(TransactionTypeEnum.ONLINE);
//                case 2 -> transaction.setTransactionType(TransactionTypeEnum.TAKE_AWAY);
//            }
//
//            String receiptNumber = generateReceiptNumber(branch.getBranchCode());
//            transaction.setReceiptNumber(receiptNumber);
//            transactionRepository.save(transaction);
//
//            for (ProductInfo productInfo : productInfos) {
//                Product product = new Product();
//                product.setProductId(UUID.randomUUID().toString());
//                product.setProductPriceId(UUID.randomUUID().toString());
//                product.setProductCode(productInfo.getProductCode());
//                product.setProductName(productInfo.getProductName());
//                product.setPrice(productInfo.getPrice());
//                product.setBranch(branch);
//                productRepository.save(product);
//                productPriceId = product.getProductPriceId();
//
//                BillDetail billDetail = new BillDetail();
//                billDetail.setBill(transaction);
//                billDetail.setProduct(product);
//                billDetail.setQuantity(1);
//                billDetail.setBillDetailId(UUID.randomUUID().toString());
//                billDetail.setTotalSales(product.getPrice().multiply(BigDecimal.valueOf(2)));
//                billDetailRepository.save(billDetail);
//                billDetails.add(billDetail);
//            }
//        }
//
//
//        mockMvc.perform(get("/api/transactions/total-sales")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpectAll(status().isOk())
//                .andDo(result -> {
//                    String jsonString = result.getResponse().getContentAsString();
//                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
//                    });
//
//                    assertNull(mapResponse.get("errors"));
//                    assertInstanceOf(Map.class, mapResponse.get("data"));
//
//                    System.out.println("data : " + mapResponse.get("data"));
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                    System.out.println("paging : " + mapResponse.get("paging"));
//                    System.out.println();
//                });
//    }
//
//    public String generateReceiptNumber(String branchCode) {
//        Integer year = LocalDateTime.now().getYear();
//        SequenceNumber sequence = sequenceNumberRepository.findByBranchCodeAndYear(branchCode, year);
//        if (Objects.isNull(sequence)) {
//            sequence = new SequenceNumber();
//            sequence.setBranchCode(branchCode);
//            sequence.setYear(year);
//            sequence.setCurrentValue(1);
//        } else {
//            sequence.setCurrentValue(sequence.getCurrentValue() + 1);
//        }
//
//        String formatReceiptNumber = String.format("%s-%d-%03d", branchCode, year, sequence.getCurrentValue());
//        sequenceNumberRepository.save(sequence);
//
//        return formatReceiptNumber;
//    }
//}