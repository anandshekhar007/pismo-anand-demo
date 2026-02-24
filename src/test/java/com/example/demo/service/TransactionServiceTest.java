package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.model.OperationType;
import com.example.demo.model.TransactionRecord;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.OperationTypeRepository;
import com.example.demo.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private OperationTypeRepository operationTypeRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void createTransaction_shouldReturnEmpty_whenAccountNotFound() {
        // given
        when(accountRepository.findById(1L))
                .thenReturn(Optional.empty());

        // when
        Optional<TransactionRecord> result =
                transactionService.createTransaction(1L, 4, 100.0);

        // then
        assertThat(result).isEmpty();

        verify(accountRepository).findById(1L);
        verifyNoInteractions(operationTypeRepository, transactionRepository);
    }

    @Test
    void createTransaction_shouldReturnEmpty_whenOperationTypeNotFound() {
        // given
        Account account = new Account("12345678900");
        account.setAccountId(1L);

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));
        when(operationTypeRepository.findById(99))
                .thenReturn(Optional.empty());

        // when
        Optional<TransactionRecord> result =
                transactionService.createTransaction(1L, 99, 100.0);

        // then
        assertThat(result).isEmpty();

        verify(accountRepository).findById(1L);
        verify(operationTypeRepository).findById(99);
        verifyNoInteractions(transactionRepository);
    }

    @Test
    void createTransaction_shouldStorePositiveAmount_whenCreditVoucher() {
        // given
        Account account = new Account("12345678900");
        account.setAccountId(1L);

        OperationType creditVoucher =
                new OperationType(4, "Credit Voucher");

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));
        when(operationTypeRepository.findById(4))
                .thenReturn(Optional.of(creditVoucher));
        when(transactionRepository.save(any(TransactionRecord.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Optional<TransactionRecord> result =
                transactionService.createTransaction(1L, 4, -60.0);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getAmount()).isEqualTo(60.0);

        verify(transactionRepository).save(any(TransactionRecord.class));
    }

    @Test
    void createTransaction_shouldStoreNegativeAmount_whenNotCreditVoucher() {
        // given
        Account account = new Account("12345678900");
        account.setAccountId(1L);

        OperationType withdrawal =
                new OperationType(3, "Withdrawal");

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));
        when(operationTypeRepository.findById(3))
                .thenReturn(Optional.of(withdrawal));
        when(transactionRepository.save(any(TransactionRecord.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Optional<TransactionRecord> result =
                transactionService.createTransaction(1L, 3, 60.0);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getAmount()).isEqualTo(-60.0);

        verify(transactionRepository).save(any(TransactionRecord.class));
    }
}