package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public class AccountRequest {

    @NotBlank
    private String documentNumber;

    public AccountRequest() {}

    public AccountRequest(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }
}
