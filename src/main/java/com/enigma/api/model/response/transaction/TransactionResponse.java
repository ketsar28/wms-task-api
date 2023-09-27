package com.enigma.api.model.response.transaction;

import com.enigma.api.model.response.orderdetail.OrderDetailResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {

    private String billId;

    private String receiptNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date transDate;

    private String transactionType;

    private List<OrderDetailResponse> billDetails;
}
