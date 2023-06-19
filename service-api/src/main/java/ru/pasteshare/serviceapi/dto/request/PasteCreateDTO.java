package ru.pasteshare.serviceapi.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class PasteCreateDTO {
    private UUID userId;
    private String title;
    private String content;
    private LocalDateTime expirationDate;
}

