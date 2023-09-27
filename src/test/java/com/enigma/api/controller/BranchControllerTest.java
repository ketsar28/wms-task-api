//package com.enigma.api.controller;
//
//import com.enigma.api.entity.Branch;
//import com.enigma.api.repository.BranchRepository;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class BranchControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    private BranchRepository branchRepository;
//
//    private static String id;
//    private static String branchCode;
//
//    @BeforeEach
//    void setUp() {
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
//                    assertNull(mapResponse.get("errors"));
//                    assertNotNull(data.get("branchId"));
//                    assertEquals(request.get("branchName"), data.get("branchName"));
//                    assertEquals(request.get("address"), data.get("address"));
//                    assertEquals(request.get("phoneNumber"), data.get("phoneNumber"));
//
//                    id = data.get("branchId").toString();
//                    branchCode = data.get("branchCode").toString();
//
//                    System.out.println("data : " + data);
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                });
//    }
//
//    @Order(2)
//    @Test
//    public void testCreateBranchFailedBadRequest() throws Exception {
//        Map<String, Object> request = new HashMap<>();
//
//        mockMvc.perform(post("/api/branch")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsString(request))
//                ).andExpectAll(status().isBadRequest())
//                .andDo(result -> {
//                    String jsonString = result.getResponse().getContentAsString();
//                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
//                    });
//                    Map<String, Object> data = (Map<String, Object>) mapResponse.get("data");
//                    assertNotNull(mapResponse.get("errors"));
//                    assertNull(data);
//
//                    System.out.println("data : " + data);
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                });
//    }
//
//    @Order(3)
//    @Test
//    public void testCreateBranchFailedDataDuplicate() throws Exception {
//        Branch branch = new Branch();
//        branch.setBranchId(UUID.randomUUID().toString());
//        branch.setBranchName("Cilandak");
//        branch.setBranchCode("0205");
//        branch.setAddress("Jl. Cilandak Raya No.XX");
//        branch.setPhoneNumber("08123441234");
//        branchRepository.save(branch);
//
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
//                ).andExpectAll(status().isConflict())
//                .andDo(result -> {
//                    String jsonString = result.getResponse().getContentAsString();
//                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
//                    });
//
//                    Map<String, Object> data = (Map<String, Object>) mapResponse.get("data");
//                    assertNotNull(mapResponse.get("errors"));
//                    assertNull(data);
//
//                    System.out.println("data : " + data);
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                });
//    }
//
//    @Order(4)
//    @Test
//    public void testGetAllBranch() throws Exception {
//        String page = "0";
//        String size = "10";
//
//        for (int i = 0; i < 2; i++) {
//            Branch branch = new Branch();
//            branch.setBranchId(UUID.randomUUID().toString() + i);
//            branch.setBranchName("Cilandak-" + i);
//            branch.setBranchCode("0205-" + i);
//            branch.setAddress("Jl. Cilandak Raya No.XX" + i);
//            branch.setPhoneNumber("08123441234" + i);
//            branchRepository.save(branch);
//
//        }
//
//        mockMvc.perform(get("/api/branch")
//                        .param("page", page)
//                        .param("size", size)
//                        .accept(MediaType.APPLICATION_JSON_VALUE))
//                .andExpectAll(status().isOk())
//                .andDo(result -> {
//                    String jsonString = result.getResponse().getContentAsString();
//                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
//                    });
//
//                    List<Object> data = (List<Object>) mapResponse.get("data");
//                    assertNull(mapResponse.get("errors"));
//                    assertInstanceOf(List.class, data);
//                    assertNotNull(mapResponse.get("paging"));
//
//                    System.out.println("data : " + data);
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                    System.out.println("paging : " + mapResponse.get("paging"));
//                });
//    }
//
//    @Order(5)
//    @Test
//    public void testGetBranchById() throws Exception {
//        Branch branch = new Branch();
//        branch.setBranchId(UUID.randomUUID().toString());
//        branch.setBranchName("Cilandak");
//        branch.setBranchCode("0205");
//        branch.setAddress("Jl. Cilandak Raya No.XX");
//        branch.setPhoneNumber("08123441234");
//        branchRepository.save(branch);
//
//        id = branch.getBranchId();
//
//        mockMvc.perform(get("/api/branch/" + id)
//                        .accept(MediaType.APPLICATION_JSON_VALUE))
//                .andExpectAll(status().isOk())
//                .andDo(result -> {
//                    String jsonString = result.getResponse().getContentAsString();
//                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
//                    });
//
//                    Map<String, Object> data = (Map<String, Object>) mapResponse.get("data");
//                    assertNull(mapResponse.get("errors"));
//                    assertNotNull(data.get("branchId"));
//                    assertNotNull(data.get("branchName"));
//                    assertNotNull(data.get("address"));
//                    assertNotNull(data.get("phoneNumber"));
//
//                    System.out.println("data : " + data);
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                });
//    }
//
//    @Order(5)
//    @Test
//    public void testUpdateBranch() throws Exception {
//        Branch branch = new Branch();
//        branch.setBranchId(UUID.randomUUID().toString());
//        branch.setBranchName("Cilandak");
//        branch.setBranchCode("0205");
//        branch.setAddress("Jl. Cilandak Raya No.XX");
//        branch.setPhoneNumber("08123441234");
//        branchRepository.save(branch);
//
//        id = branch.getBranchId();
//        branchCode = branch.getBranchCode();
//
//        Map<String, Object> request = new HashMap<>();
//        request.put("branchId", id);
//        request.put("branchName", "Cilandak KKO");
//        request.put("branchCode", branchCode);
//        request.put("address", "Jl. Cilandak Raya No.XI");
//        request.put("phoneNumber", "08123441234");
//
//        mockMvc.perform(put("/api/branch")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpectAll(status().isOk())
//                .andDo(result -> {
//                    String jsonString = result.getResponse().getContentAsString();
//                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
//                    });
//
//                    Map<String, Object> data = (Map<String, Object>) mapResponse.get("data");
//                    assertNull(mapResponse.get("errors"));
//                    assertNotNull(data.get("branchId"));
//                    assertEquals(request.get("branchName"), data.get("branchName"));
//                    assertEquals(request.get("address"), data.get("address"));
//                    assertEquals(request.get("phoneNumber"), data.get("phoneNumber"));
//
//                    System.out.println("data : " + data);
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                });
//    }
//
//    @Order(5)
//    @Test
//    public void testUpdateBranchFailedNoId() throws Exception {
//        Branch branch = new Branch();
//        branch.setBranchId(UUID.randomUUID().toString());
//        branch.setBranchName("Cilandak");
//        branch.setBranchCode("0205");
//        branch.setAddress("Jl. Cilandak Raya No.XX");
//        branch.setPhoneNumber("08123441234");
//        branchRepository.save(branch);
//
//        id = branch.getBranchId() + "- Not Found";
//        branchCode = branch.getBranchCode() + "- Not Found";
//
//        Map<String, Object> request = new HashMap<>();
//        request.put("branchName", "Cilandak KKO");
//        request.put("branchCode", branchCode);
//        request.put("address", "Jl. Cilandak Raya No.XI");
//        request.put("phoneNumber", "08123441234");
//
//        mockMvc.perform(put("/api/branch")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpectAll(status().isBadRequest())
//                .andDo(result -> {
//                    String jsonString = result.getResponse().getContentAsString();
//                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
//                    });
//
//                    Map<String, Object> data = (Map<String, Object>) mapResponse.get("data");
//                    assertNotNull(mapResponse.get("errors"));
//                    assertNull(data);
//
//                    System.out.println("data : " + data);
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                });
//    }
//
//    @Order(6)
//    @Test
//    public void testDeleteBranch() throws Exception {
//        Branch branch = new Branch();
//        branch.setBranchId(UUID.randomUUID().toString());
//        branch.setBranchName("Cilandak");
//        branch.setBranchCode("0205");
//        branch.setAddress("Jl. Cilandak Raya No.XX");
//        branch.setPhoneNumber("08123441234");
//        branchRepository.save(branch);
//
//        id = branch.getBranchId();
//
//        mockMvc.perform(delete("/api/branch/" + id).accept(MediaType.APPLICATION_JSON_VALUE))
//                .andExpectAll(status().isOk())
//                .andDo(result -> {
//                    String jsonString = result.getResponse().getContentAsString();
//                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
//                    });
//                    String data = (String) mapResponse.get("data");
//                    assertNull(mapResponse.get("errors"));
//                    assertEquals("OK", data);
//
//                    System.out.println("data : " + data);
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                });
//    }
//
//    @Order(7)
//    @Test
//    public void testDeleteBranchFailedNoId() throws Exception {
//        Branch branch = new Branch();
//        branch.setBranchId(UUID.randomUUID().toString());
//        branch.setBranchName("Cilandak");
//        branch.setBranchCode("0205");
//        branch.setAddress("Jl. Cilandak Raya No.XX");
//        branch.setPhoneNumber("08123441234");
//        branchRepository.save(branch);
//
//        id = branch.getBranchId() + "-wrong";
//        mockMvc.perform(delete("/api/branch/" + id).accept(MediaType.APPLICATION_JSON_VALUE))
//                .andExpectAll(status().isNotFound())
//                .andDo(result -> {
//                    String jsonString = result.getResponse().getContentAsString();
//                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
//                    });
//                    String data = (String) mapResponse.get("data");
//                    assertNotNull(mapResponse.get("errors"));
//                    assertNull(data);
//
//                    System.out.println("data : " + data);
//                    System.out.println("errors : " + mapResponse.get("errors"));
//                });
//    }
//
//
//}