package ru.pasteshare.serviceapi.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pasteshare.serviceapi.dto.request.UserRegisterDTO;
import ru.pasteshare.serviceapi.exception.NotFoundException;
import ru.pasteshare.serviceapi.exception.UserExistsException;
import ru.pasteshare.serviceapi.model.User;
import ru.pasteshare.serviceapi.repository.RoleRepository;
import ru.pasteshare.serviceapi.repository.UserRepository;
import ru.pasteshare.serviceapi.service.UserService;
import ru.pasteshare.serviceapi.service.mapper.UserMapper;
import ru.pasteshare.serviceapi.util.Status;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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
        user.setRoles(userDTO.getRoles().stream()
                .map(role -> roleRepository.findByName(role.getName())
                        .orElseThrow(() -> new RuntimeException("Role not found")))
                .collect(Collectors.toSet()));
        user.setStatus(Status.ACTIVE);
        User savedUser = userRepository.save(user);
        logger.info("Registered new user with username: {}", savedUser.getUsername());
        return savedUser;
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) throws NotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        logger.debug("Retrieved user with username: {}", username);
        return user.get();
    }

}
