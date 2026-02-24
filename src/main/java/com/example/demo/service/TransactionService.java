package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.model.OperationType;
import com.example.demo.model.TransactionRecord;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.OperationTypeRepository;
import com.example.demo.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final OperationTypeRepository operationTypeRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository, OperationTypeRepository operationTypeRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.operationTypeRepository = operationTypeRepository;
    }

    @Transactional
    public Optional<TransactionRecord> createTransaction(Long accountId, Integer operationTypeId, Double amount) {
        Optional<Account> accountOpt = accountRepository.findById(accountId);
        if (accountOpt.isEmpty()) {
            return Optional.empty();
        }
        Optional<OperationType> opOpt = operationTypeRepository.findById(operationTypeId);
        if (opOpt.isEmpty()) {
            return Optional.empty();
        }


        double normalizedAmount = (amount == null) ? 0.0 : amount.doubleValue();
        if (operationTypeId == 4) {

            normalizedAmount = Math.abs(normalizedAmount);
        } else {

            normalizedAmount = -Math.abs(normalizedAmount);
        }

        TransactionRecord tx = new TransactionRecord(accountOpt.get(), opOpt.get(), normalizedAmount, OffsetDateTime.now());
        TransactionRecord saved = transactionRepository.save(tx);
        return Optional.of(saved);
    }
}
