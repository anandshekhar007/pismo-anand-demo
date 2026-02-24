package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;

public class TransactionRequest {

    @NotNull
    private Long accountId;

    @NotNull
    private Integer operationTypeId;

    @NotNull
    private Double amount;

    public TransactionRequest() {}

    public TransactionRequest(Long accountId, Integer operationTypeId, Double amount) {
        this.accountId = accountId;
        this.operationTypeId = operationTypeId;
        this.amount = amount;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Integer getOperationTypeId() {
        return operationTypeId;
    }

    public void setOperationTypeId(Integer operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
