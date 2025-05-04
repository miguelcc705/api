// package com.api.api.controller;

// import com.api.api.model.User;
// import com.api.api.service.AuthService;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.Map;
// import java.util.Optional;

// @RestController
// @RequestMapping("/api/auth")
// public class AuthController {

//     private final AuthService authService;

//     public AuthController(AuthService authService) {
//         this.authService = authService;
//     }

//     @PostMapping("/register")
//     public ResponseEntity<?> register(@RequestBody User user) {
//         User newUser = authService.register(user);
//         return ResponseEntity.ok(Map.of("message", "User registered successfully", "user", newUser));
//     }

//     @PostMapping("/login")
//     public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
//         String email = loginData.get("email");
//         String password = loginData.get("password");

//         Optional<String> tokenOpt = authService.login(email, password);
//         if (tokenOpt.isPresent()) {
//             return ResponseEntity.ok(Map.of("token", tokenOpt.get()));
//         } else {
//             return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
//         }
//     }
// }


// src/main/java/com/api/api/controller/AuthController.java
package com.api.api.controller;

import com.api.api.dto.LoginRequest;
import com.api.api.model.User;
import com.api.api.service.AuthService;
import com.api.api.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AuthService authService;
    @Autowired private JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(authService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        return authService.login(req.username(), req.password())
                .map(token -> ResponseEntity.ok(Map.of("token", token)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                               .body(Map.of("error", "Credenciales inválidas")));
    }
}
