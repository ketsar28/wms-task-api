package com.enigma.api.model.response.orderdetail;

import com.enigma.api.model.response.product.ProductResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponse {

    private String billDetailId;

    private String billId;

    private ProductResponse product;

    private Integer quantity;

    private BigDecimal totalSales;

    @JsonProperty("totalSales")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public BigDecimal getTotalSalesForResponse() {
        return totalSales;
    }

}
