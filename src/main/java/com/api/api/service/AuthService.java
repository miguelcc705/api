package com.api.api.service;

import com.api.api.model.User;
import com.api.api.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;


@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expirationMs}")
    private long jwtExpirationMs;


    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {
        // System.out.println("Buscando usuario: " + user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public Optional<String> login(String username, String rawPassword) {
        // System.out.println("Buscando usuario: " + username);
        Optional<User> userOpt = userRepository.findByUsername(username);
        // System.out.println("User opt: " + userOpt);
        // System.out.println("userOpt.isPresent() " + userOpt.isPresent());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // System.out.println("Usuario encontrado: " + user.getUsername());
            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
                // System.out.println("Contraseña correcta");
                // System.out.println("Longitud de la clave: " + jwtSecret.getBytes(StandardCharsets.UTF_8).length);
                // System.out.println("JWT Secret Length: " + jwtSecret.length());
                // System.out.println("JWT Secret: " + jwtSecret);

                Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
                // System.out.println("Longitud de la clave: " + key);

                String token = Jwts.builder()
                        .setSubject(user.getUsername())
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs)) // 1 día
                        .signWith(SignatureAlgorithm.HS512, key)
                        .compact();
                return Optional.of(token);
            } else {
                System.out.println("Contraseña incorrecta");
            }
        } else {
            System.out.println("Usuario no encontrado");
        }
        return Optional.empty();
    }
    
}
