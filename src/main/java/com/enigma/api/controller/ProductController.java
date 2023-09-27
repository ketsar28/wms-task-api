package com.enigma.api.controller;

import com.enigma.api.model.request.product.ProductRequest;
import com.enigma.api.model.request.product.SearchProductRequest;
import com.enigma.api.model.response.general.CommonResponse;
import com.enigma.api.model.response.general.PagingResponse;
import com.enigma.api.model.response.product.ProductResponse;
import com.enigma.api.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest request) {
        ProductResponse response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .data(response)
                        .build());
    }

    @GetMapping
    public ResponseEntity<?> getAllProductsList(@RequestParam(value= "size", required = false, defaultValue = "10") Integer size,
                                             @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                             @RequestParam(value = "productCode", required = false) String productCode,
                                             @RequestParam(value = "productName", required = false) String productName,
                                             @RequestParam(value = "minPrice", required = false) String minPrice,
                                             @RequestParam(value = "maxPrice", required = false) String maxPrice) {

        SearchProductRequest request = SearchProductRequest.builder()
                .size(size)
                .page(page)
                .productCode(productCode)
                .productName(productName)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .build();

        Page<ProductResponse> productsList = productService.getAllProducts(request);

        PagingResponse paging = PagingResponse.builder()
                .count(productsList.getNumberOfElements())
                .totalPage(productsList.getTotalPages())
                .page(productsList.getNumber())
                .size(productsList.getSize())
                .build();


        return ResponseEntity.ok(CommonResponse.builder()
                .data(productsList.getContent())
                .paging(paging)
                .build());
    }


    @GetMapping(path = "/{id_product}/product")
    public ResponseEntity<?> getProductById(@PathVariable("id_product") String productId) {
        ProductResponse response = productService.getProductById(productId);
        return ResponseEntity.ok(CommonResponse.builder()
                        .data(response)
                        .build());
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody ProductRequest request) {
        ProductResponse response = productService.updateProduct(request);
        return ResponseEntity.ok(CommonResponse.builder()
                        .data(response)
                        .build());
    }


    @GetMapping(path = "/{id_branch}")
    public ResponseEntity<?> getProductByBranchId(@PathVariable("id_branch") String branchId) {
        Page<ProductResponse> responses = productService.getAllProductsByBranchId(branchId);
        PagingResponse paging = PagingResponse.builder()
                .page(responses.getNumber())
                .size(responses.getSize())
                .count(responses.getNumberOfElements())
                .totalPage(responses.getTotalPages())
                .build();

        return ResponseEntity.ok(CommonResponse.builder()
                .data(responses.getContent())
                .paging(paging)
                .build());
    }

    @DeleteMapping(path = "/{id_product}")
    public ResponseEntity<?> deleteProductById(@PathVariable("id_product") String productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok(CommonResponse.builder()
                .data("OK")
                .build());
    }

}
