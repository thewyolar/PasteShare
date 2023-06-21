package ru.pasteshare.serviceapi.service;

import ru.pasteshare.serviceapi.dto.request.PasteCreateDTO;
import ru.pasteshare.serviceapi.dto.response.UserPasteDTO;
import ru.pasteshare.serviceapi.exception.NoAccessException;

import java.util.List;
import java.util.UUID;

public interface PasteService {
    UserPasteDTO create(PasteCreateDTO paste) throws NoAccessException;
    List<UserPasteDTO> getUserPastes(UUID userId, int page, int size) throws NoAccessException;
}
