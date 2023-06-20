package ru.pasteshare.serviceapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pasteshare.serviceapi.dto.request.AuthRequestDTO;
import ru.pasteshare.serviceapi.dto.request.RefreshTokenRequestDTO;
import ru.pasteshare.serviceapi.exception.NotFoundException;
import ru.pasteshare.serviceapi.exception.RefreshTokenException;
import ru.pasteshare.serviceapi.service.AuthService;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO authRequestDTO) throws NotFoundException {
        return ResponseEntity.ok(authService.login(authRequestDTO));
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) throws RefreshTokenException {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequestDTO));
    }
}