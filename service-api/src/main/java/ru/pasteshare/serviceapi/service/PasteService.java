package ru.pasteshare.serviceapi.service;

import org.springframework.stereotype.Service;

@Service
public class PasteService {
    private final PasteService pasteService;

    public PasteService(PasteService pasteService) {
        this.pasteService = pasteService;
    }
}
