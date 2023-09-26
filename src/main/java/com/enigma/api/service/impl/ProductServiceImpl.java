package com.enigma.api.service.impl;

import com.enigma.api.entity.Branch;
import com.enigma.api.entity.Product;
import com.enigma.api.model.request.product.ProductRequest;
import com.enigma.api.model.response.branch.BranchResponse;
import com.enigma.api.model.response.product.ProductResponse;
import com.enigma.api.repository.BranchRepository;
import com.enigma.api.repository.ProductRepository;
import com.enigma.api.service.interfaces.BranchService;
import com.enigma.api.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final BranchService branchService;

    private static void saveToDb(ProductRequest request, Product product, BranchResponse isBranchExists) {
        product.setPrice(request.getPrice());
        product.setProductId(request.getProductId());
        product.setProductCode(request.getProductCode());
        product.setProductName(request.getProductName());
        product.setProductPriceId(UUID.randomUUID().toString());
        product.setBranch(Branch.builder()
                        .branchId(isBranchExists.getBranchId())
                        .address(isBranchExists.getAddress())
                        .branchName(isBranchExists.getBranchName())
                        .phoneNumber(isBranchExists.getPhoneNumber())
                .build());
    }

    private static ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .productCode(product.getProductCode())
                .productName(product.getProductName())
                .productPriceId(product.getProductPriceId())
                .price(product.getPrice())
                .branch(BranchResponse.builder()
                        .branchId(product.getBranch().getBranchId())
                        .branchCode(product.getBranch().getBranchCode())
                        .branchName(product.getBranch().getBranchName())
                        .address(product.getBranch().getAddress())
                        .phoneNumber(product.getBranch().getPhoneNumber())
                        .build())
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

        return toProductResponse(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return null;
    }

    @Override
    public ProductResponse updateProduct(ProductRequest request) {
        return null;
    }

    @Override
    public void deleteProduct(String productId) {
        productRepository.deleteById(productId);
    }
}
