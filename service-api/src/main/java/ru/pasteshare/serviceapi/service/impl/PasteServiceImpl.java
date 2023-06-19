package ru.pasteshare.serviceapi.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pasteshare.serviceapi.dto.request.PasteCreateDTO;
import ru.pasteshare.serviceapi.exception.NoAccessException;
import ru.pasteshare.serviceapi.model.Paste;
import ru.pasteshare.serviceapi.repository.PasteRepository;
import ru.pasteshare.serviceapi.service.AccessControlService;
import ru.pasteshare.serviceapi.service.PasteService;
import ru.pasteshare.serviceapi.service.mapper.PasteMapper;
import ru.pasteshare.serviceapi.util.Status;

import java.time.LocalDateTime;

@Service
public class PasteServiceImpl implements PasteService {
    private final AccessControlService accessControlService;
    private final PasteRepository pasteRepository;
    private final PasteMapper pasteMapper;

    public PasteServiceImpl(AccessControlService accessControlService, PasteRepository pasteRepository, PasteMapper pasteMapper) {
        this.accessControlService = accessControlService;
        this.pasteRepository = pasteRepository;
        this.pasteMapper = pasteMapper;
    }

    @Override
    @Transactional
    public Paste create(PasteCreateDTO pasteCreating) throws NoAccessException {
        accessControlService.checkAccess(pasteCreating.getUserId());
        Paste paste = pasteMapper.toPaste(pasteCreating);
        paste.setUser(accessControlService.getUserInfo().getUser());
        paste.setCreatedAt(LocalDateTime.now());
        paste.setUpdatedAt(paste.getCreatedAt());
        if (pasteCreating.getExpirationDate() != null) {
            paste.setExpiredAt(pasteCreating.getExpirationDate());
        }
        paste.setStatus(Status.ACTIVE);
        return pasteRepository.save(paste);
    }
}
