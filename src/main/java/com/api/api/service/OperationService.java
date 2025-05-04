package com.api.api.service;

import com.api.api.dto.CalculateRequest;
import com.api.api.dto.OperationResponse;
import com.api.api.model.Operation;
import com.api.api.model.OperationType;
import com.api.api.model.User;
import com.api.api.repository.OperationRepository;
import com.api.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class OperationService {

    @Autowired private OperationRepository operationRepository;
    @Autowired private UserRepository userRepository;

    public OperationResponse calculate(CalculateRequest req) {
        // Validations
        if (req.getOperandA().abs().compareTo(BigDecimal.valueOf(1_000_000)) > 0 ||
            (req.getOperandB() != null && req.getOperandB().abs().compareTo(BigDecimal.valueOf(1_000_000)) > 0)) {
            throw new IllegalArgumentException("Operandos fuera de rango");
        }
        if (req.getOperation() == OperationType.DIVISION && req.getOperandB().compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("División por cero no permitida");
        }
        if (req.getOperation() == OperationType.SQUARE_ROOT && req.getOperandA().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Raíz cuadrada de número negativo no permitida");
        }

        BigDecimal result;
        switch (req.getOperation()) {
            case ADDITION -> result = req.getOperandA().add(req.getOperandB());
            case SUBTRACTION -> result = req.getOperandA().subtract(req.getOperandB());
            case MULTIPLICATION -> result = req.getOperandA().multiply(req.getOperandB());
            case DIVISION -> result = req.getOperandA().divide(req.getOperandB(), 8, RoundingMode.HALF_UP);
            case POWER -> result = req.getOperandA().pow(req.getOperandB().intValue());
            case SQUARE_ROOT -> result = BigDecimal.valueOf(Math.sqrt(req.getOperandA().doubleValue()));
            default -> throw new UnsupportedOperationException("Operación no soportada");
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Operation op = new Operation();
        op.setOperation(req.getOperation());
        op.setOperandA(req.getOperandA());
        op.setOperandB(req.getOperandB());
        op.setResult(result);
        op.setTimestamp(LocalDateTime.now());
        op.setUserId(user.getId());

        Operation saved = operationRepository.save(op);
        return new OperationResponse(
            saved.getId(), saved.getOperation(), saved.getOperandA(), saved.getOperandB(),
            saved.getResult(), saved.getTimestamp(), saved.getUserId()
        );
    }

    public Page<OperationResponse> getHistory(UUID userId, Optional<OperationType> type,
                                              Optional<LocalDateTime> start, Optional<LocalDateTime> end,
                                              Pageable pageable) {
        Page<Operation> page;
        if (type.isPresent()) {
            page = operationRepository.findByUserIdAndOperation(userId, type.get(), pageable);
        } else if (start.isPresent() && end.isPresent()) {
            page = operationRepository.findByUserIdAndTimestampBetween(userId, start.get(), end.get(), pageable);
        } else {
            page = operationRepository.findByUserId(userId, pageable);
        }
        return page.map(op -> new OperationResponse(
            op.getId(), op.getOperation(), op.getOperandA(), op.getOperandB(),
            op.getResult(), op.getTimestamp(), op.getUserId()
        ));
    }

    public OperationResponse getById(UUID id) {
        Operation op = operationRepository.findById(id)
                      .orElseThrow(() -> new IllegalArgumentException("Operación no encontrada"));
        return new OperationResponse(
            op.getId(), op.getOperation(), op.getOperandA(), op.getOperandB(),
            op.getResult(), op.getTimestamp(), op.getUserId()
        );
    }

    // public void deleteById(UUID id) {
    //     operationRepository.deleteById(id);
    // }
    public boolean deleteById(UUID id) {
        if (operationRepository.existsById(id)) {
            operationRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}