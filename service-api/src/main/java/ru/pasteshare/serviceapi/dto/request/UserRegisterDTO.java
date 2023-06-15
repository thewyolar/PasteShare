package ru.pasteshare.serviceapi.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.pasteshare.serviceapi.model.Role;

import java.util.Set;

@Getter
@Setter
public class UserRegisterDTO {
    @NotNull
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
    private String email;

    @NotNull
    @Size(min = 2)
    private String username;

    @NotNull
    @Size(min = 4)
    private String password;

    @NotNull
    private Set<Role> roles;
}
