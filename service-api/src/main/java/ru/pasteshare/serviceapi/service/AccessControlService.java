package ru.pasteshare.serviceapi.service;

import ru.pasteshare.serviceapi.exception.NoAccessException;
import ru.pasteshare.serviceapi.security.impl.UserDetailsImpl;

import java.util.UUID;

public interface AccessControlService {
    UserDetailsImpl getUserInfo();
    void checkAccess(UUID userId) throws NoAccessException;
}