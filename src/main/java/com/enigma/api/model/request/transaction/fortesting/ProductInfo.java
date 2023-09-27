package com.enigma.api.model.request.transaction.fortesting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfo {

    private String productCode;

    private String productName;

    private BigDecimal price;

}
