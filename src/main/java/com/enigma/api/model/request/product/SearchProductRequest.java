package com.enigma.api.model.request.product;

import lombok.*;
import org.springframework.http.MediaType;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SearchProductRequest {
    @NotNull(message = "size is required")
    private Integer size;

    @NotNull(message = "page is required")
    private Integer page;

    private String productCode;

    private String productName;

    private String minPrice;

    private String maxPrice;
}
