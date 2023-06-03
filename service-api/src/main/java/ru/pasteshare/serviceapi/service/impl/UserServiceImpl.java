package ru.pasteshare.serviceapi.service.impl;

import org.springframework.stereotype.Service;
import ru.pasteshare.serviceapi.dto.UserRegisterDTO;
import ru.pasteshare.serviceapi.exception.UserExistsException;
import ru.pasteshare.serviceapi.model.User;
import ru.pasteshare.serviceapi.repository.UserRepository;
import ru.pasteshare.serviceapi.service.UserService;
import ru.pasteshare.serviceapi.service.mapper.UserMapper;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User register(UserRegisterDTO userDTO) throws UserExistsException {
        userDTO.setPassword(userDTO.getPassword());
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new UserExistsException(userDTO.getUsername());
        }
        User user = userMapper.toUser(userDTO);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(user.getCreatedAt());
        return userRepository.save(user);
    }
}
