package com.api.api.dto;

import com.api.api.model.OperationType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class OperationResponse {

    private UUID id;
    private OperationType operation;
    private BigDecimal operandA;
    private BigDecimal operandB;
    private BigDecimal result;
    private LocalDateTime timestamp;
    private UUID userId;

    public OperationResponse() {
        // Constructor vacío para deserialización
    }

    public OperationResponse(UUID id,
                             OperationType operation,
                             BigDecimal operandA,
                             BigDecimal operandB,
                             BigDecimal result,
                             LocalDateTime timestamp,
                             UUID userId) {
        this.id = id;
        this.operation = operation;
        this.operandA = operandA;
        this.operandB = operandB;
        this.result = result;
        this.timestamp = timestamp;
        this.userId = userId;
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public OperationType getOperation() {
        return operation;
    }
    public void setOperation(OperationType operation) {
        this.operation = operation;
    }

    public BigDecimal getOperandA() {
        return operandA;
    }
    public void setOperandA(BigDecimal operandA) {
        this.operandA = operandA;
    }

    public BigDecimal getOperandB() {
        return operandB;
    }
    public void setOperandB(BigDecimal operandB) {
        this.operandB = operandB;
    }

    public BigDecimal getResult() {
        return result;
    }
    public void setResult(BigDecimal result) {
        this.result = result;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public UUID getUserId() {
        return userId;
    }
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
