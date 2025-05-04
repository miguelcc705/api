package com.api.api.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "operations")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID userId;

    @Enumerated(EnumType.STRING)
    private OperationType operation;

    @Column(precision = 19, scale = 8)
    private BigDecimal operandA;

    @Column(precision = 19, scale = 8)
    private BigDecimal operandB;

    @Column(precision = 19, scale = 8)
    private BigDecimal result;

    private LocalDateTime timestamp;

    // Getters y setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public OperationType getOperation() { return operation; }
    public void setOperation(OperationType operation) { this.operation = operation; }

    public BigDecimal getOperandA() { return operandA; }
    public void setOperandA(BigDecimal operandA) { this.operandA = operandA; }

    public BigDecimal getOperandB() { return operandB; }
    public void setOperandB(BigDecimal operandB) { this.operandB = operandB; }

    public BigDecimal getResult() { return result; }
    public void setResult(BigDecimal result) { this.result = result; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
