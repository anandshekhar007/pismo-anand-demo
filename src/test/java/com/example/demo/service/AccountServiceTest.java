package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void createAccountIfNotExists_shouldCreateAccount_whenDocumentDoesNotExist() {
        // given
        String documentNumber = "12345678900";
        Account savedAccount = new Account(documentNumber);
        savedAccount.setAccountId(1L);

        when(accountRepository.findByDocumentNumber(documentNumber))
                .thenReturn(Optional.empty());
        when(accountRepository.save(any(Account.class)))
                .thenReturn(savedAccount);

        // when
        Optional<Account> result =
                accountService.createAccountIfNotExists(documentNumber);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getDocumentNumber())
                .isEqualTo(documentNumber);

        verify(accountRepository).findByDocumentNumber(documentNumber);
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void createAccountIfNotExists_shouldReturnEmpty_whenDocumentAlreadyExists() {
        // given
        String documentNumber = "12345678900";
        Account existingAccount = new Account(documentNumber);

        when(accountRepository.findByDocumentNumber(documentNumber))
                .thenReturn(Optional.of(existingAccount));

        // when
        Optional<Account> result =
                accountService.createAccountIfNotExists(documentNumber);

        // then
        assertThat(result).isEmpty();

        verify(accountRepository).findByDocumentNumber(documentNumber);
        verify(accountRepository, never()).save(any());
    }

    @Test
    void findById_shouldReturnAccount_whenAccountExists() {
        // given
        Long accountId = 1L;
        Account account = new Account("12345678900");
        account.setAccountId(accountId);

        when(accountRepository.findById(accountId))
                .thenReturn(Optional.of(account));

        // when
        Optional<Account> result = accountService.findById(accountId);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getAccountId()).isEqualTo(accountId);

        verify(accountRepository).findById(accountId);
    }
}