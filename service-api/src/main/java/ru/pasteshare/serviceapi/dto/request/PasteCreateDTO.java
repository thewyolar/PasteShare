package ru.pasteshare.serviceapi.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PasteCreateDTO {
    private String title;
    private String content;
    private LocalDateTime expirationDate;
}

