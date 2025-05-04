package com.api.api.controller;

import com.api.api.dto.CalculateRequest;
import com.api.api.dto.OperationResponse;
import com.api.api.model.OperationType;
import com.api.api.model.User;
import com.api.api.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.api.api.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class OperationController {

    @Autowired private OperationService operationService;
    @Autowired private UserRepository userRepository;

    @PostMapping("/calculate")
    public ResponseEntity<OperationResponse> calculate(@RequestBody CalculateRequest req) {
        return ResponseEntity.ok(operationService.calculate(req));
    }

    @GetMapping("/history")
    public ResponseEntity<Page<OperationResponse>> history(
            @RequestParam Optional<OperationType> type,
            @RequestParam Optional<LocalDateTime> start,
            @RequestParam Optional<LocalDateTime> end,
            Pageable pageable) {
                
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
            UUID userId = user.getId();
        return ResponseEntity.ok(operationService.getHistory(userId, type, start, end, pageable));
    }

    @GetMapping("/history/{id}")
    public ResponseEntity<OperationResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(operationService.getById(id));
    }

    @DeleteMapping("/history/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable UUID id) {
        boolean deleted = operationService.deleteById(id);
        Map<String, String> response = new HashMap<>();
        if (deleted) {
            response.put("message", "Elemento eliminado exitosamente");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "El ID proporcionado no existe o ya fue eliminado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
