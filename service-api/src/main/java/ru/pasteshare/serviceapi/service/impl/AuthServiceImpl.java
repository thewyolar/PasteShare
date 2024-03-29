package ru.pasteshare.serviceapi.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pasteshare.serviceapi.dto.request.AuthRequestDTO;
import ru.pasteshare.serviceapi.dto.request.RefreshTokenRequestDTO;
import ru.pasteshare.serviceapi.dto.response.AuthResponseDTO;
import ru.pasteshare.serviceapi.exception.NotFoundException;
import ru.pasteshare.serviceapi.exception.RefreshTokenException;
import ru.pasteshare.serviceapi.model.RefreshToken;
import ru.pasteshare.serviceapi.security.JwtProvider;
import ru.pasteshare.serviceapi.service.AuthService;
import ru.pasteshare.serviceapi.service.RefreshTokenService;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtProvider jwtProvider, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    @Transactional
    public AuthResponseDTO login(AuthRequestDTO authRequestDTO) throws NotFoundException {
        try {
            String username = authRequestDTO.getUsername();
            logger.info("Login attempt for username: {}", username);
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, authRequestDTO.getPassword()));
            if (authentication.isAuthenticated()) {
                RefreshToken refreshToken = refreshTokenService.createRefreshToken(username);
                logger.info("User {} logged in successfully", username);
                return AuthResponseDTO.builder()
                        .accessToken(jwtProvider.generateToken(username))
                        .refreshToken(refreshToken.getToken()).build();
            } else {
                logger.warn("Login failed for username: {}", username);
                throw new NotFoundException(username);
            }
        } catch (AuthenticationException e) {
            logger.warn("Invalid username or password for login attempt");
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Override
    @Transactional
    public AuthResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO) throws RefreshTokenException {
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtProvider.generateToken(user.getUsername());
                    return AuthResponseDTO.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenRequestDTO.getToken())
                            .build();
                }).orElseThrow(() ->
                    new RefreshTokenException("Refresh token is not in database!"));
    }
}
