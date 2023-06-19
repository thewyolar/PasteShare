package ru.pasteshare.serviceapi.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.pasteshare.serviceapi.dto.request.PasteCreateDTO;
import ru.pasteshare.serviceapi.exception.NoAccessException;
import ru.pasteshare.serviceapi.service.PasteService;

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
    public ResponseEntity<?> create(@Valid @RequestBody PasteCreateDTO pasteCreating) throws NoAccessException {
        return new ResponseEntity<>(pasteService.create(pasteCreating), HttpStatus.OK);
    }
}
