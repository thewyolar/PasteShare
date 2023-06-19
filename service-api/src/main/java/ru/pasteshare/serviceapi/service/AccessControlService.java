package ru.pasteshare.serviceapi.service;

import ru.pasteshare.serviceapi.exception.NoAccessException;
import ru.pasteshare.serviceapi.security.UserInfo;

import java.util.UUID;

public interface AccessControlService {
    UserInfo getUserInfo();
    void checkAccess(UUID userId) throws NoAccessException;
}