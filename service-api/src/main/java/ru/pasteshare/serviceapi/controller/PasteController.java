package ru.pasteshare.serviceapi.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.pasteshare.serviceapi.dto.request.PasteCreateDTO;
import ru.pasteshare.serviceapi.dto.response.UserPasteDTO;
import ru.pasteshare.serviceapi.exception.NoAccessException;
import ru.pasteshare.serviceapi.exception.NotFoundException;
import ru.pasteshare.serviceapi.service.PasteService;

import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/pastes")
public class PasteController {
    private final PasteService pasteService;

    public PasteController(PasteService pasteService) {
        this.pasteService = pasteService;
    }

    @PostMapping
    @Secured("ROLE_ADMIN,ROLE_USER")
    public ResponseEntity<?> createPaste(@Valid @RequestBody PasteCreateDTO pasteCreating,
                                         UriComponentsBuilder uriComponentsBuilder) throws NoAccessException {
        UserPasteDTO userPasteDTO = pasteService.createPaste(pasteCreating);
        return ResponseEntity.created(uriComponentsBuilder
                        .path("api/pastes/{pasteId}")
                        .build(Map.of("pasteId", userPasteDTO.getId())))
                .body(userPasteDTO);
    }

    @GetMapping
    @Secured("ROLE_ADMIN,ROLE_USER")
    public ResponseEntity<?> getUserPastes(@RequestParam("userId") UUID userId,
                                 @RequestParam("page") int page,
                                 @RequestParam("size") int size) throws NoAccessException, NotFoundException {
        return ResponseEntity.ok()
                .body(pasteService.getUserPastes(userId, page, size));
    }

    @GetMapping("/{pasteId}")
    @Secured("ROLE_ADMIN,ROLE_USER")
    public ResponseEntity<?> getPasteById(@PathVariable("pasteId") UUID pasteId) throws NotFoundException {
        return ResponseEntity.ok()
                .body(pasteService.getPasteById(pasteId));
    }
}
