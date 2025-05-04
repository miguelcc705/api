package com.api.api.repository;

import com.api.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, UUID> {

    // Puedes agregar métodos personalizados si quieres, por ejemplo:
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}

