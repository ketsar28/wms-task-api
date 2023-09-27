package com.enigma.api.service.interfaces;

import com.enigma.api.model.request.product.ProductRequest;
import com.enigma.api.model.request.product.SearchProductRequest;
import com.enigma.api.model.response.product.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);
    ProductResponse getProductById(String productId);
    Page<ProductResponse> getAllProducts(SearchProductRequest request);
    Page<ProductResponse> getAllProductsByBranchId(String branchId);
    ProductResponse updateProduct(ProductRequest request);
    void deleteProduct(String productId);
}
