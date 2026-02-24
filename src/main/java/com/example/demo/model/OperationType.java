package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "operation_types")
public class OperationType {

    @Id
    private Integer operationTypeId;

    @Column(nullable = false)
    private String description;

    public OperationType() {}

    public OperationType(Integer operationTypeId, String description) {
        this.operationTypeId = operationTypeId;
        this.description = description;
    }

    public Integer getOperationTypeId() {
        return operationTypeId;
    }

    public void setOperationTypeId(Integer operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
