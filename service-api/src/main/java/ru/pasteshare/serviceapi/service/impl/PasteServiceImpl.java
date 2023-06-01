package ru.pasteshare.serviceapi.service.impl;

import org.springframework.stereotype.Service;
import ru.pasteshare.serviceapi.dto.PasteCreateDTO;
import ru.pasteshare.serviceapi.exception.NoAccessException;
import ru.pasteshare.serviceapi.model.Paste;
import ru.pasteshare.serviceapi.repository.PasteRepository;
import ru.pasteshare.serviceapi.service.PasteService;
import ru.pasteshare.serviceapi.service.mapper.PasteMapper;

@Service
public class PasteServiceImpl implements PasteService {
    private final PasteRepository pasteRepository;
    private final PasteMapper pasteMapper;

    public PasteServiceImpl(PasteRepository pasteRepository, PasteMapper pasteMapper) {
        this.pasteRepository = pasteRepository;
        this.pasteMapper = pasteMapper;
    }

    @Override
    public Paste create(PasteCreateDTO paste) throws NoAccessException {
        return pasteRepository.save(pasteMapper.toPaste(paste));
    }
}
