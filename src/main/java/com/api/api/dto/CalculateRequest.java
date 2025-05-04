package com.api.api.dto;

import com.api.api.model.OperationType;
import java.math.BigDecimal;

public class CalculateRequest {

    private OperationType operation;  
    private BigDecimal operandA;
    private BigDecimal operandB;

    // Getters y Setters
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
}
