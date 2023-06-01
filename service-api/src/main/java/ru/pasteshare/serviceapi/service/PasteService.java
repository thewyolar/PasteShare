package ru.pasteshare.serviceapi.service;

import org.springframework.stereotype.Service;
import ru.pasteshare.serviceapi.dto.PasteCreateDTO;
import ru.pasteshare.serviceapi.exception.NoAccessException;
import ru.pasteshare.serviceapi.model.Paste;

@Service
public interface PasteService {
    Paste create(PasteCreateDTO paste) throws NoAccessException;
}
