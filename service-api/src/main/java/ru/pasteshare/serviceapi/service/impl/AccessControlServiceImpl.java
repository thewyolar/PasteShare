package ru.pasteshare.serviceapi.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.pasteshare.serviceapi.exception.NoAccessException;
import ru.pasteshare.serviceapi.model.Role;
import ru.pasteshare.serviceapi.model.User;
import ru.pasteshare.serviceapi.security.UserInfo;
import ru.pasteshare.serviceapi.service.AccessControlService;

import java.util.Set;
import java.util.UUID;

@Service
public class AccessControlServiceImpl implements AccessControlService {
    private final Logger logger = LoggerFactory.getLogger(AccessControlServiceImpl.class);

    @Override
    public UserInfo getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserInfo) {
            return (UserInfo) authentication.getPrincipal();
        }
        return null;
    }

    @Override
    public void checkAccess(UUID userId) throws NoAccessException {
        User user = getUserInfo().getUser();
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            if (role.getName().equals("ROLE_ADMIN") || role.getName().equals("ROLE_USER")) {
                return;
            }
        }
        if (!userId.equals(user.getId())) {
            logger.warn("Access denied for user with id={}", userId);
            throw new NoAccessException("No access user by id=" + userId);
        }
    }
}
