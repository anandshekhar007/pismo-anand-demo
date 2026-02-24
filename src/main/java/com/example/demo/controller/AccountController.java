package com.example.demo.controller;

import com.example.demo.dto.AccountRequest;
import com.example.demo.dto.AccountResponse;
import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import com.example.demo.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody AccountRequest request) {
        return accountService.createAccountIfNotExists(request.getDocumentNumber())
                .map(saved -> ResponseEntity.ok(new AccountResponse(saved.getAccountId(), saved.getDocumentNumber())))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable Long accountId) {
        return accountService.findById(accountId)
                .map(a -> ResponseEntity.ok(new AccountResponse(a.getAccountId(), a.getDocumentNumber())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
