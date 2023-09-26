package com.enigma.api.controller;

import com.enigma.api.model.request.product.ProductRequest;
import com.enigma.api.model.response.general.CommonResponse;
import com.enigma.api.model.response.product.ProductResponse;
import com.enigma.api.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping(path = "/{id_product}")
    public ResponseEntity<?> getProductById(@PathVariable("id_product") String productId) {
        ProductResponse response = productService.getProductById(productId);
        return ResponseEntity.ok(CommonResponse.builder()
                        .data(response)
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
