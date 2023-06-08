package ru.pasteshare.serviceapi.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.pasteshare.serviceapi.exception.NotFoundException;
import ru.pasteshare.serviceapi.model.RefreshToken;
import ru.pasteshare.serviceapi.repository.RefreshTokenRepository;
import ru.pasteshare.serviceapi.repository.UserRepository;
import ru.pasteshare.serviceapi.service.RefreshTokenService;
import ru.pasteshare.serviceapi.service.UserService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${jwt.token.refresh.expiration}")
    private long jwtRefreshExpiration;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, UserService userService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userService = userService;
    }

    @Override
    public RefreshToken createRefreshToken(String username) throws NotFoundException {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        RefreshToken refreshToken = RefreshToken.builder()
                .user(userService.findByUsername(username))
                .token(UUID.randomUUID().toString())
                .expiredAt(LocalDateTime.from(zonedDateTime.plusSeconds(jwtRefreshExpiration)))
                .build();
        refreshToken.setCreatedAt(zonedDateTime.toLocalDateTime());
        refreshToken.setUpdatedAt(refreshToken.getCreatedAt());
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiredAt().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }
}
