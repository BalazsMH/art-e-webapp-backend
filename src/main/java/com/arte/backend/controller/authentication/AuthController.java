package com.arte.backend.controller.authentication;

import com.arte.backend.service.authentication.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestParam(name = "password") String password,
                                        @RequestParam(name = "email") String email) {
        return authService.login(password, email);
    }
}
