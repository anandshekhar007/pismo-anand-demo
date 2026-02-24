package com.example.demo.controller;

import com.example.demo.dto.TransactionRequest;
import com.example.demo.dto.TransactionResponse;
import com.example.demo.model.TransactionRecord;
import com.example.demo.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<?> createTransaction(@Valid @RequestBody TransactionRequest request) {
        Optional<TransactionRecord> saved = transactionService.createTransaction(request.getAccountId(), request.getOperationTypeId(), request.getAmount());
        if (saved.isEmpty()) {
            return ResponseEntity.badRequest().body("Account or operation type not found");
        }
        TransactionRecord t = saved.get();
        TransactionResponse resp = new TransactionResponse(
                t.getTransactionId(),
                t.getAccount().getAccountId(),
                t.getOperationType().getOperationTypeId(),
                t.getAmount(),
                t.getEventDate(),
                t.getOperationType().getDescription()
        );
        return ResponseEntity.ok(resp);
    }
}
