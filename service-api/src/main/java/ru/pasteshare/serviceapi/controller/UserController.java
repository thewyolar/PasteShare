package ru.pasteshare.serviceapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.pasteshare.serviceapi.dto.request.UserRegisterDTO;
import ru.pasteshare.serviceapi.dto.response.RegisteredUserDTO;
import ru.pasteshare.serviceapi.exception.UserExistsException;
import ru.pasteshare.serviceapi.service.UserService;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDTO userDTO,
                                      UriComponentsBuilder uriComponentsBuilder) throws UserExistsException {
        RegisteredUserDTO registeredUserDTO = userService.register(userDTO);
        return ResponseEntity.created(uriComponentsBuilder
                        .path("api/users/{userId}")
                        .build(Map.of("userId", registeredUserDTO.getId())))
                .body(registeredUserDTO);
    }
}
