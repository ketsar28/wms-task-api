package com.enigma.api.service.interfaces;

import com.enigma.api.model.request.product.ProductRequest;
import com.enigma.api.model.response.product.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);
    ProductResponse getProductById(String productId);
    List<ProductResponse> getAllProducts();
    ProductResponse updateProduct(ProductRequest request);
    void deleteProduct(String productId);
}
