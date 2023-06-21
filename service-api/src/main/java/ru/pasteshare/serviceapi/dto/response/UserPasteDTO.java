package ru.pasteshare.serviceapi.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPasteDTO {
    private UUID id;
    private String title;
    private String content;
    private String language;
    private LocalDateTime expiredAt;
}
