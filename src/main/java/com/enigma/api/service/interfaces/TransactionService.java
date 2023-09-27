package com.enigma.api.service.interfaces;

import com.enigma.api.model.request.transaction.SearchTransactionRequest;
import com.enigma.api.model.response.transaction.TransactionResponse;
import com.enigma.api.model.request.transaction.request.CreateTransactionRequest;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.Map;

public interface TransactionService {
    TransactionResponse createTransaction(CreateTransactionRequest request);
    TransactionResponse getTransaction(String orderId);
    Page<TransactionResponse> getTransactionList(SearchTransactionRequest request);
    Map<String, BigDecimal> getAllSales();

}
