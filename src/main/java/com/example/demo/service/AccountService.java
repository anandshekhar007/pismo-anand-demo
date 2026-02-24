package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Optional<Account> createAccountIfNotExists(String documentNumber) {
        if (accountRepository.findByDocumentNumber(documentNumber).isPresent()) {
            return Optional.empty();
        }
        Account account = new Account(documentNumber);
        Account saved = accountRepository.save(account);
        return Optional.of(saved);
    }

    public Optional<Account> findById(Long accountId) {
        return accountRepository.findById(accountId);
    }
}
