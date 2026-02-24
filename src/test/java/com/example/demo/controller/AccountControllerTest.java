package com.example.demo.controller;

import com.example.demo.dto.AccountRequest;
import com.example.demo.model.Account;
import com.example.demo.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc(addFilters = false)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void createAccount_shouldReturn200_whenAccountCreated() throws Exception {

        Account account = new Account("12345678900");
        account.setAccountId(1L);

        Mockito.when(accountService.createAccountIfNotExists("12345678900"))
                .thenReturn(Optional.of(account));

        AccountRequest request = new AccountRequest("12345678900");

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.account_id").value(1))
                .andExpect(jsonPath("$.document_number").value("12345678900"));
    }

    @Test
    void getAccount_shouldReturn404_whenNotFound() throws Exception {

        Mockito.when(accountService.findById(99L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/accounts/99"))
                .andExpect(status().isNotFound());

    }
}