package ru.pasteshare.serviceapi.service.impl;

import org.springframework.stereotype.Service;
import ru.pasteshare.serviceapi.dto.PasteCreateDTO;
import ru.pasteshare.serviceapi.exception.NoAccessException;
import ru.pasteshare.serviceapi.model.Paste;
import ru.pasteshare.serviceapi.model.User;
import ru.pasteshare.serviceapi.repository.PasteRepository;
import ru.pasteshare.serviceapi.service.PasteService;
import ru.pasteshare.serviceapi.service.mapper.PasteMapper;

import java.time.LocalDateTime;

@Service
public class PasteServiceImpl implements PasteService {
    private final PasteRepository pasteRepository;
    private final PasteMapper pasteMapper;

    public PasteServiceImpl(PasteRepository pasteRepository, PasteMapper pasteMapper) {
        this.pasteRepository = pasteRepository;
        this.pasteMapper = pasteMapper;
    }

    @Override
    public Paste create(PasteCreateDTO pasteCreating) throws NoAccessException {
        Paste paste = pasteMapper.toPaste(pasteCreating);
        paste.setCreatedAt(LocalDateTime.now());
        paste.setUpdatedAt(paste.getCreatedAt());
        if (pasteCreating.getExpirationDate() != null) {
            paste.setExpiredAt(pasteCreating.getExpirationDate());
        }
        return pasteRepository.save(paste);
    }
}
