package ru.pasteshare.serviceapi.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.pasteshare.serviceapi.dto.request.UserRegisterDTO;
import ru.pasteshare.serviceapi.dto.response.RegisteredUserDTO;
import ru.pasteshare.serviceapi.exception.NotFoundException;
import ru.pasteshare.serviceapi.exception.UserExistsException;
import ru.pasteshare.serviceapi.model.User;
import ru.pasteshare.serviceapi.repository.UserRepository;
import ru.pasteshare.serviceapi.service.mapper.UserMapper;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testRegister_NewUser_ReturnsRegisteredUserDTO() throws UserExistsException {
        // given
        UserRegisterDTO userDTO = new UserRegisterDTO();
        userDTO.setUsername("testuser");
        userDTO.setPassword("password123");
        userDTO.setRoles(Collections.emptySet());

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());

        when(userRepository.existsByUsername(userDTO.getUsername())).thenReturn(false);
        when(userMapper.toUser(userDTO)).thenReturn(user);
        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        // when
        RegisteredUserDTO result = userService.register(userDTO);

        // then
        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());
        verify(userRepository, times(1)).existsByUsername(userDTO.getUsername());
        verify(userMapper, times(1)).toUser(userDTO);
        verify(passwordEncoder, times(1)).encode(userDTO.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testRegister_UserExists_ThrowsUserExistsException() {
        // given
        UserRegisterDTO userDTO = new UserRegisterDTO();
        userDTO.setUsername("testuser");
        userDTO.setPassword("password123");
        userDTO.setRoles(Collections.emptySet());

        when(userRepository.existsByUsername(userDTO.getUsername())).thenReturn(true);

        // when & then
        assertThrows(UserExistsException.class, () -> userService.register(userDTO));
        verify(userRepository, times(1)).existsByUsername(userDTO.getUsername());
        verify(userMapper, never()).toUser(userDTO);
        verify(passwordEncoder, never()).encode(userDTO.getPassword());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testFindByUsername_UserExists_ReturnsUser() throws NotFoundException {
        // given
        String username = "testuser";
        User user = new User();
        user.setUsername(username);

        Optional<User> optionalUser = Optional.of(user);

        when(userRepository.findByUsername(username)).thenReturn(optionalUser);

        // when
        User result = userService.findByUsername(username);

        // then
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    public void testFindByUsername_UserNotExists_ThrowsNotFoundException() {
        // given
        String username = "testuser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> userService.findByUsername(username));
        verify(userRepository, times(1)).findByUsername(username);
    }
}
