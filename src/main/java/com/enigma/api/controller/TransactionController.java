package com.enigma.api.controller;

import com.enigma.api.model.request.transaction.SearchTransactionRequest;
import com.enigma.api.model.response.general.CommonResponse;
import com.enigma.api.model.response.general.PagingResponse;
import com.enigma.api.model.response.transaction.TransactionResponse;
import com.enigma.api.service.interfaces.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.enigma.api.model.request.transaction.request.CreateTransactionRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping(path = "/api/transactions",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody CreateTransactionRequest transactionRequest) {
        TransactionResponse response = transactionService.createTransaction(transactionRequest);
        log.info("response controller : {}", response);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .data(response)
                        .build());
    }

    @GetMapping(path = "/api/transactions/{id_bill}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> get(@PathVariable("id_bill") String billId) {
        TransactionResponse response = transactionService.getTransaction(billId);
        return ResponseEntity.ok(CommonResponse.builder().data(response).build());
    }


    @GetMapping(path = "/api/transactions",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<List<TransactionResponse>>> getAllTransactionsList(@RequestParam(name = "receiptNumber", required = false) String receiptNumber,
                                                                                         @RequestParam(name = "transType", required = false) String transType,
                                                                                         @RequestParam(name = "productName", required = false) String productName,
                                                                                         @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                                                         @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {


        SearchTransactionRequest transactionRequest = SearchTransactionRequest.builder()
                .page(page)
                .size(size)
                .receiptNumber(receiptNumber)
                .transType(transType)
                .productName(productName)
                .build();

        Page<TransactionResponse> transactionsList = transactionService.getTransactionList(transactionRequest);

        PagingResponse paging = PagingResponse.builder()
                .count(transactionsList.getNumberOfElements())
                .totalPage(transactionsList.getTotalPages())
                .page(transactionsList.getNumber())
                .size(transactionsList.getSize())
                .build();

        return ResponseEntity.ok(CommonResponse.<List<TransactionResponse>>builder()
                .data(transactionsList.getContent())
                .paging(paging)
                .build());

    }

    @GetMapping(path = "/api/transactions/total-sales",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllSales() {
        Map<String, BigDecimal> response = transactionService.getAllSales();
        return ResponseEntity.ok(CommonResponse.builder().data(response).build());
    }
}