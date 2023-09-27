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
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class ProductControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    private BranchRepository branchRepository;
//    @Autowired
//    private ProductRepository productRepository;
//    private static String branchId;
//    private static String productId;
//    private static String productCode;
//
//    @BeforeEach
//    void setUp() {
//        productRepository.deleteAll();
//        branchRepository.deleteAll();
//    }
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
//                });
//    }
//
//    @Order(2)
//    @Test
//    public void testCreateProduct() throws Exception {
//        Branch createBranch = new Branch();
//        createBranch.setBranchId(UUID.randomUUID().toString());
//        createBranch.setBranchName("Cilandak");
//        createBranch.setBranchCode("0205");
//        createBranch.setAddress("Jl. Cilandak Raya No.XX");
//        createBranch.setPhoneNumber("08123441234");
//        branchRepository.save(createBranch);
//
//        branchId = createBranch.getBranchId();
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
//                    Map<String, Object> branch = (Map<String, Object>) data.get("branch");
//
//                    assertNull(mapResponse.get("errors"));
//                    assertNotNull(data.get("productPriceId"));
//                    assertEquals(request.get("productCode"), data.get("productCode"));
//                    assertEquals(request.get("productName"), data.get("productName"));
//                    assertEquals(request.get("price"), data.get("price"));
//                    assertInstanceOf(Map.class, branch);
//                    assertEquals(request.get("branchId"), branch.get("branchId"));
//
//                    productId = data.get("productId").toString();
//                    productCode = data.get("productCode").toString();
//
//                    System.out.println("data : " + data);
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                    System.out.println();
//
//                    System.out.println("product id : " + data.get("productId"));
//                    System.out.println("product price id : " + data.get("productPriceId"));
//                    System.out.println("product name : " + data.get("productName"));
//                    System.out.println("price : " + data.get("price"));
//                    System.out.println();
//
//                    System.out.println("branch id : " + branch.get("branchId"));
//                    System.out.println("branch code : " + branch.get("branchCode"));
//                    System.out.println("branch name : " + branch.get("branchName"));
//                    System.out.println("adress : " + branch.get("address"));
//                    System.out.println("phone number :" + branch.get("phoneNumber"));
//                    System.out.println();
//                });
//    }
//
//    @Order(3)
//    @Test
//    public void testGetAllProduct() throws Exception {
//        Branch createBranch = new Branch();
//        createBranch.setBranchId(UUID.randomUUID().toString());
//        createBranch.setBranchName("Cilandak");
//        createBranch.setBranchCode("0205");
//        createBranch.setAddress("Jl. Cilandak Raya No.XX");
//        createBranch.setPhoneNumber("08123441234");
//        branchRepository.save(createBranch);
//
//        branchId = createBranch.getBranchId();
//
//        Product product = new Product();
//        product.setProductId(UUID.randomUUID().toString());
//        product.setProductPriceId(UUID.randomUUID().toString());
//        product.setProductCode("01-001");
//        product.setProductName("Nasi Putih");
//        product.setPrice(new BigDecimal(BigInteger.valueOf(5000)));
//        product.setBranch(createBranch);
//        productRepository.save(product);
//
//        productId = product.getProductId();
//        productCode = product.getProductCode();
//
//        mockMvc.perform(get("/api/products")
//                        .param("page", "0")
//                        .param("size", "10")
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                ).andExpectAll(status().isOk())
//                .andDo(result -> {
//                    String jsonString = result.getResponse().getContentAsString();
//                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
//                    });
//                    List<Object> data = (List<Object>) mapResponse.get("data");
//                    assertNull(mapResponse.get("errors"));
//                    assertInstanceOf(List.class, data);
//                    assertNotNull(mapResponse.get("paging"));
//
//                    System.out.println("data : " + data);
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                    System.out.println();
//
//                });
//    }
//
//    @Order(4)
//    @Test
//    public void testGetAllProductByBranchId() throws Exception {
//        Branch createBranch = new Branch();
//        createBranch.setBranchId(UUID.randomUUID().toString());
//        createBranch.setBranchName("Cilandak");
//        createBranch.setBranchCode("0205");
//        createBranch.setAddress("Jl. Cilandak Raya No.XX");
//        createBranch.setPhoneNumber("08123441234");
//        branchRepository.save(createBranch);
//
//        branchId = createBranch.getBranchId();
//
//        Product product = new Product();
//        product.setProductId(UUID.randomUUID().toString());
//        product.setProductPriceId(UUID.randomUUID().toString());
//        product.setProductCode("01-001");
//        product.setProductName("Nasi Putih");
//        product.setPrice(new BigDecimal(BigInteger.valueOf(5000)));
//        product.setBranch(createBranch);
//        productRepository.save(product);
//
//        productId = product.getProductId();
//        productCode = product.getProductCode();
//
//        mockMvc.perform(get("/api/products/" + branchId)
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                ).andExpectAll(status().isOk())
//                .andDo(result -> {
//                    String jsonString = result.getResponse().getContentAsString();
//                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
//                    });
//                    List<Object> data = (List<Object>) mapResponse.get("data");
//                    assertNull(mapResponse.get("errors"));
//                    assertInstanceOf(List.class, data);
//
//                    System.out.println("data : " + data);
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                    System.out.println();
//                });
//    }
//
//    @Order(5)
//    @Test
//    public void testGetAllProductByBranchIdFailedInvalidBranchId() throws Exception {
//        mockMvc.perform(get("/api/products/xxx")
//                        .accept(MediaType.APPLICATION_JSON_VALUE))
//                .andExpectAll(status().isNotFound())
//                .andDo(result -> {
//                    String jsonString = result.getResponse().getContentAsString();
//                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
//                    });
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
//    public void testUpdateProduct() throws Exception {
//        Branch createBranch = new Branch();
//        createBranch.setBranchId(UUID.randomUUID().toString());
//        createBranch.setBranchName("Cilandak");
//        createBranch.setBranchCode("0205");
//        createBranch.setAddress("Jl. Cilandak Raya No.XX");
//        createBranch.setPhoneNumber("08123441234");
//        branchRepository.save(createBranch);
//
//        branchId = createBranch.getBranchId();
//
//        Product product = new Product();
//        product.setProductId(UUID.randomUUID().toString());
//        product.setProductPriceId(UUID.randomUUID().toString());
//        product.setProductCode("01-001");
//        product.setProductName("Nasi Putih");
//        product.setPrice(new BigDecimal(BigInteger.valueOf(5000)));
//        product.setBranch(createBranch);
//        productRepository.save(product);
//
//        productId = product.getProductId();
//        productCode = product.getProductCode();
//
//        Map<String, Object> request = new HashMap<>();
//        request.put("productId", productId);
//        request.put("productCode", productCode);
//        request.put("productName", "Nasi Merah");
//        request.put("price", 7000.00);
//        request.put("branchId", branchId);
//
//        mockMvc.perform(put("/api/products")
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpectAll(status().isOk())
//                .andDo(result -> {
//                    String jsonString = result.getResponse().getContentAsString();
//                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
//                    });
//                    Map<String, Object> data = (Map<String, Object>) mapResponse.get("data");
//                    assertEquals(request.get("productName"), data.get("productName"));
//                    assertEquals(request.get("price"), data.get("price"));
//                    assertNull(mapResponse.get("errors"));
//
//                    System.out.println("data : " + data);
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                    System.out.println();
//
//                    System.out.println("product id : " + data.get("productId"));
//                    System.out.println("product price id : " + data.get("productPriceId"));
//                    System.out.println("product name : " + data.get("productName"));
//                    System.out.println("price : " + data.get("price"));
//                    System.out.println();
//                });
//    }
//
//    @Order(7)
//    @Test
//    public void testUpdateProductFailedInvalidId() throws Exception {
//        Branch createBranch = new Branch();
//        createBranch.setBranchId(UUID.randomUUID().toString());
//        createBranch.setBranchName("Cilandak");
//        createBranch.setBranchCode("0205");
//        createBranch.setAddress("Jl. Cilandak Raya No.XX");
//        createBranch.setPhoneNumber("08123441234");
//        branchRepository.save(createBranch);
//
//        branchId = createBranch.getBranchId();
//
//        Product product = new Product();
//        product.setProductId(UUID.randomUUID().toString());
//        product.setProductPriceId(UUID.randomUUID().toString());
//        product.setProductCode("01-001");
//        product.setProductName("Nasi Putih");
//        product.setPrice(new BigDecimal(BigInteger.valueOf(5000)));
//        product.setBranch(createBranch);
//        productRepository.save(product);
//
//        productId = product.getProductId();
//        productCode = product.getProductCode();
//
//        Map<String, Object> request = new HashMap<>();
//        request.put("productId", "xxx");
//        request.put("productCode", productCode + "-wrong");
//        request.put("productName", "Nasi Merah");
//        request.put("price", 7000);
//        request.put("branchId", branchId + "-wrong");
//
//        mockMvc.perform(put("/api/products")
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpectAll(status().isNotFound())
//                .andDo(result -> {
//                    String jsonString = result.getResponse().getContentAsString();
//                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
//                    });
//                    assertNotNull(mapResponse.get("errors"));
//                    assertNull(mapResponse.get("data"));
//
//                    System.out.println("data : " + mapResponse.get("data"));
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                });
//    }
//
//    @Order(8)
//    @Test
//    public void testDeleteById() throws Exception {
//        Branch createBranch = new Branch();
//        createBranch.setBranchId(UUID.randomUUID().toString());
//        createBranch.setBranchName("Cilandak");
//        createBranch.setBranchCode("0205");
//        createBranch.setAddress("Jl. Cilandak Raya No.XX");
//        createBranch.setPhoneNumber("08123441234");
//        branchRepository.save(createBranch);
//
//        branchId = createBranch.getBranchId();
//
//        Product product = new Product();
//        product.setProductId(UUID.randomUUID().toString());
//        product.setProductPriceId(UUID.randomUUID().toString());
//        product.setProductCode("01-001");
//        product.setProductName("Nasi Putih");
//        product.setPrice(new BigDecimal(BigInteger.valueOf(5000)));
//        product.setBranch(createBranch);
//        productRepository.save(product);
//
//        productId = product.getProductId();
//        productCode = product.getProductCode();
//
//        mockMvc.perform(delete("/api/products/" + productId)
//                .accept(MediaType.APPLICATION_JSON_VALUE))
//                .andExpectAll(status().isOk())
//                .andDo(result -> {
//                    String jsonString = result.getResponse().getContentAsString();
//                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
//                    });
//                    assertNull(mapResponse.get("errors"));
//                    assertEquals("OK", mapResponse.get("data"));
//
//                    System.out.println("data : " + mapResponse.get("data"));
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                });
//    }
//
//    @Order(9)
//    @Test
//    public void testDeleteByIdFailedNoId() throws Exception {
//        Branch createBranch = new Branch();
//        createBranch.setBranchId(UUID.randomUUID().toString());
//        createBranch.setBranchName("Cilandak");
//        createBranch.setBranchCode("0205");
//        createBranch.setAddress("Jl. Cilandak Raya No.XX");
//        createBranch.setPhoneNumber("08123441234");
//        branchRepository.save(createBranch);
//
//        branchId = createBranch.getBranchId();
//
//        Product product = new Product();
//        product.setProductId(UUID.randomUUID().toString());
//        product.setProductPriceId(UUID.randomUUID().toString());
//        product.setProductCode("01-001");
//        product.setProductName("Nasi Putih");
//        product.setPrice(new BigDecimal(BigInteger.valueOf(5000)));
//        product.setBranch(createBranch);
//        productRepository.save(product);
//
//        productId = product.getProductId();
//        productCode = product.getProductCode();
//
//        mockMvc.perform(delete("/api/products/" + productId + "-wrong")
//                        .accept(MediaType.APPLICATION_JSON_VALUE))
//                .andExpectAll(status().isNotFound())
//                .andDo(result -> {
//                    String jsonString = result.getResponse().getContentAsString();
//                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
//                    });
//                    assertNotNull(mapResponse.get("errors"));
//                    assertNull(mapResponse.get("data"));
//
//                    System.out.println("data : " + mapResponse.get("data"));
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                });
//    }
//
//    @Order(10)
//    @Test
//    public void testDeleteBranch() throws Exception {
//        Branch createBranch = new Branch();
//        createBranch.setBranchId(UUID.randomUUID().toString());
//        createBranch.setBranchName("Cilandak");
//        createBranch.setBranchCode("0205");
//        createBranch.setAddress("Jl. Cilandak Raya No.XX");
//        createBranch.setPhoneNumber("08123441234");
//        branchRepository.save(createBranch);
//
//        branchId = createBranch.getBranchId();
//
//        mockMvc.perform(delete("/api/branch/" + branchId).accept(MediaType.APPLICATION_JSON_VALUE))
//                .andExpectAll(status().isOk())
//                .andDo(result -> {
//                    String jsonString = result.getResponse().getContentAsString();
//                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
//                    });
//                    String data = (String) mapResponse.get("data");
//                    assertNull(mapResponse.get("errors"));
//                    assertEquals("OK", data);
//
//                    System.out.println("data : " + mapResponse.get("data"));
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                });
//    }
//
//}