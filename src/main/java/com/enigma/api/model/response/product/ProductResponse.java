package com.enigma.api.model.response.product;
import com.enigma.api.model.response.branch.BranchResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ProductResponse {
    private String productId;
    private String productPriceId;
    private String productCode;
    private String productName;
    private BigDecimal price;
    private BranchResponse branch;

    @JsonProperty("productPriceId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getTotalSalesForResponse() {
        return productPriceId;
    }

}
