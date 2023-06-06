package ru.pasteshare.serviceapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.pasteshare.serviceapi.model.User;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponseDTO {
    private User user;
    private String token;
}
