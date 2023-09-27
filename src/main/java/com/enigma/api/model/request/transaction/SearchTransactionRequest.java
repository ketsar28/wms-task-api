package com.enigma.api.model.request.transaction;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchTransactionRequest {
    @NotNull
    private Integer page;

    @NotNull
    private Integer size;

    private String receiptNumber;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private String startDate;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private String endDate;

    private String transType;

    private String productName;
}
