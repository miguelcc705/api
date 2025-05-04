package com.api.api.repository;

import com.api.api.model.Operation;
import com.api.api.model.OperationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.UUID;

public interface OperationRepository extends JpaRepository<Operation, UUID> {
    Page<Operation> findByUserId(UUID userId, Pageable pageable);
    Page<Operation> findByUserIdAndOperation(UUID userId, OperationType operation, Pageable pageable);
    Page<Operation> findByUserIdAndTimestampBetween(UUID userId, LocalDateTime start, LocalDateTime end, Pageable pageable);
}