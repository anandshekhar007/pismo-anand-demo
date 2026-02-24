package com.example.demo.controller;

import com.example.demo.dto.TransactionRequest;
import com.example.demo.model.*;
import com.example.demo.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
@AutoConfigureMockMvc(addFilters = false)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createTransaction_shouldReturn200_whenValid() throws Exception {

        Account account = new Account("12345678900");
        account.setAccountId(1L);

        OperationType op = new OperationType(3, "Withdrawal");

        TransactionRecord record = new TransactionRecord(
                account,
                op,
                -60.0,
                OffsetDateTime.now()
        );
        record.setTransactionId(1L);

        Mockito.when(transactionService.createTransaction(1L, 3, 60.0))
                .thenReturn(Optional.of(record));

        TransactionRequest request =
                new TransactionRequest(1L, 3, 60.0);

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transaction_id").value(1))
                .andExpect(jsonPath("$.account_id").value(1))
                .andExpect(jsonPath("$.operation_type_id").value(3))
                .andExpect(jsonPath("$.amount").value(-60.0))
                .andExpect(jsonPath("$.operation_description").value("Withdrawal"));
    }

    @Test
    void createTransaction_shouldReturn400_whenServiceReturnsEmpty() throws Exception {

        Mockito.when(transactionService.createTransaction(1L, 3, 60.0))
                .thenReturn(Optional.empty());

        TransactionRequest request =
                new TransactionRequest(1L, 3, 60.0);

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}