package com.enigma.api.model.request.product;

import com.enigma.api.model.response.branch.BranchResponse;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ProductRequest {
    private String productId;
    @NotBlank(message = "productCode is required")
    private String productCode;
    @NotBlank(message = "productName is required")
    private String productName;
    private BigDecimal price;
    private String branchId;
}
