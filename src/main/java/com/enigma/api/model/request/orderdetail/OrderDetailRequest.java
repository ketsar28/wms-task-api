package com.enigma.api.model.request.orderdetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailRequest {

    private String billDetailId;

    @NotBlank
    private String productPriceId;

    @NotNull
    private Integer quantity;

}
