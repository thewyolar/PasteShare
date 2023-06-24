package ru.pasteshare.serviceapi.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredUserDTO {
    private UUID id;
    private String username;
    private String email;
    private String avatarUrl;
    private String location;
}
