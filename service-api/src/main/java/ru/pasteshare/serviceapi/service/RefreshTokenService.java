package ru.pasteshare.serviceapi.service;

import ru.pasteshare.serviceapi.exception.NotFoundException;
import ru.pasteshare.serviceapi.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(String username) throws NotFoundException;
    Optional<RefreshToken> findByToken(String token);
    RefreshToken verifyExpiration(RefreshToken token);
}
