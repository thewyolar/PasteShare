package ru.pasteshare.serviceapi.service;

import ru.pasteshare.serviceapi.dto.request.AuthRequestDTO;
import ru.pasteshare.serviceapi.dto.request.RefreshTokenRequestDTO;
import ru.pasteshare.serviceapi.dto.response.AuthResponseDTO;
import ru.pasteshare.serviceapi.exception.NotFoundException;
import ru.pasteshare.serviceapi.exception.RefreshTokenException;

public interface AuthService {
    AuthResponseDTO login(AuthRequestDTO authRequestDTO) throws NotFoundException;
    AuthResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO) throws RefreshTokenException;
}
