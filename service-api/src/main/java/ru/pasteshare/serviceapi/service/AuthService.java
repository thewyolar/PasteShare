package ru.pasteshare.serviceapi.service;

import ru.pasteshare.serviceapi.dto.request.AuthRequestDTO;
import ru.pasteshare.serviceapi.dto.response.AuthResponseDTO;
import ru.pasteshare.serviceapi.exception.NotFoundException;

public interface AuthService {
    AuthResponseDTO login(AuthRequestDTO authRequestDTO) throws NotFoundException;
}
