package com.example.demo.dto;

import java.time.OffsetDateTime;

public class TransactionResponse {

    private Long transactionId;
    private Long accountId;
    private Integer operationTypeId;
    private Double amount;
    private OffsetDateTime eventDate;
    private String operationDescription;

    public TransactionResponse() {}

    public TransactionResponse(Long transactionId, Long accountId, Integer operationTypeId, Double amount, OffsetDateTime eventDate, String operationDescription) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.operationTypeId = operationTypeId;
        this.amount = amount;
        this.eventDate = eventDate;
        this.operationDescription = operationDescription;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
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

    public OffsetDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(OffsetDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public String getOperationDescription() {
        return operationDescription;
    }

}
