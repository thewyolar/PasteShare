package ru.pasteshare.serviceapi.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pasteshare.serviceapi.dto.request.UserRegisterDTO;
import ru.pasteshare.serviceapi.exception.NotFoundException;
import ru.pasteshare.serviceapi.exception.UserExistsException;
import ru.pasteshare.serviceapi.model.User;
import ru.pasteshare.serviceapi.repository.UserRepository;
import ru.pasteshare.serviceapi.service.UserService;
import ru.pasteshare.serviceapi.service.mapper.UserMapper;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public User register(UserRegisterDTO userDTO) throws UserExistsException {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new UserExistsException(userDTO.getUsername());
        }
        User user = userMapper.toUser(userDTO);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(user.getCreatedAt());
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) throws NotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        return user.get();
    }
}
