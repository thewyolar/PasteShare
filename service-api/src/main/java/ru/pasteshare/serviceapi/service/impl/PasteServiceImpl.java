package ru.pasteshare.serviceapi.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pasteshare.serviceapi.dto.request.PasteCreateDTO;
import ru.pasteshare.serviceapi.dto.response.UserPasteDTO;
import ru.pasteshare.serviceapi.exception.NoAccessException;
import ru.pasteshare.serviceapi.model.Paste;
import ru.pasteshare.serviceapi.repository.PasteRepository;
import ru.pasteshare.serviceapi.service.AccessControlService;
import ru.pasteshare.serviceapi.service.PasteService;
import ru.pasteshare.serviceapi.service.mapper.PasteMapper;
import ru.pasteshare.serviceapi.util.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
    public UserPasteDTO create(PasteCreateDTO pasteCreating) throws NoAccessException {
        accessControlService.checkAccess(pasteCreating.getUserId());
        Paste paste = pasteMapper.toPaste(pasteCreating);
        paste.setUser(accessControlService.getUserInfo().getUser());
        paste.setCreatedAt(LocalDateTime.now());
        paste.setUpdatedAt(paste.getCreatedAt());
        if (pasteCreating.getExpirationDate() != null) {
            paste.setExpiredAt(pasteCreating.getExpirationDate());
        }
        paste.setStatus(Status.ACTIVE);
        return pasteMapper.toUserPasteDTO(pasteRepository.save(paste));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserPasteDTO> getUserPastes(UUID userId, int page, int size) throws NoAccessException {
        accessControlService.checkAccess(userId);
        Pageable pageable = PageRequest.of(page, size);
        return pasteMapper.toUserPasteDTOList(pasteRepository.findByUserId(userId, pageable).stream().toList());
    }
}
