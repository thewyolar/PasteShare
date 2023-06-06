package ru.pasteshare.serviceapi.service;

import ru.pasteshare.serviceapi.dto.request.PasteCreateDTO;
import ru.pasteshare.serviceapi.exception.NoAccessException;
import ru.pasteshare.serviceapi.model.Paste;

public interface PasteService {
    Paste create(PasteCreateDTO paste) throws NoAccessException;
}
