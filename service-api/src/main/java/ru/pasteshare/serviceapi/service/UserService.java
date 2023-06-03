package ru.pasteshare.serviceapi.service;

import ru.pasteshare.serviceapi.dto.UserRegisterDTO;
import ru.pasteshare.serviceapi.exception.UserExistsException;
import ru.pasteshare.serviceapi.model.User;

public interface UserService {
    User register(UserRegisterDTO user) throws UserExistsException;
}

