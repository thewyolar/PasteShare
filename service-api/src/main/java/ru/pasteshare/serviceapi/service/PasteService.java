package ru.pasteshare.serviceapi.service;

import ru.pasteshare.serviceapi.dto.request.PasteCreateDTO;
import ru.pasteshare.serviceapi.dto.response.UserPasteDTO;
import ru.pasteshare.serviceapi.exception.NoAccessException;
import ru.pasteshare.serviceapi.exception.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface PasteService {
    UserPasteDTO createPaste(PasteCreateDTO paste) throws NoAccessException;
    List<UserPasteDTO> getUserPastes(UUID userId, int page, int size) throws NoAccessException, NotFoundException;
    UserPasteDTO getPasteById(UUID pasteId) throws NotFoundException;
}
