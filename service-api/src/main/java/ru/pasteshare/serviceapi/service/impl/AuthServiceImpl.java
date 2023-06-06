package ru.pasteshare.serviceapi.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pasteshare.serviceapi.dto.request.AuthRequestDTO;
import ru.pasteshare.serviceapi.dto.response.AuthResponseDTO;
import ru.pasteshare.serviceapi.exception.NotFoundException;
import ru.pasteshare.serviceapi.model.User;
import ru.pasteshare.serviceapi.security.JwtService;
import ru.pasteshare.serviceapi.service.AuthService;
import ru.pasteshare.serviceapi.service.UserService;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponseDTO login(AuthRequestDTO authRequestDTO) throws NotFoundException {
        try {
            String username = authRequestDTO.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, authRequestDTO.getPassword()));
            User user = userService.findByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException(username);
            }
            String token = jwtService.generateToken(username);
            Map<Object, Object> response = new HashMap<>();
            response.put("authUser", user);
            response.put("token", token);

            return new AuthResponseDTO(user, token);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
