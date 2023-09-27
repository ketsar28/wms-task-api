package com.enigma.api.model.request.transaction.request;

import com.enigma.api.model.request.orderdetail.OrderDetailRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransactionRequest {
    private String billId;

    @NotBlank
    @Size(max = 100)
    private String transactionType;

    @NotNull
    private List<OrderDetailRequest> billDetails;

}
