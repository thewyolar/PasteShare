package ru.pasteshare.serviceapi.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.pasteshare.serviceapi.model.Role;
import ru.pasteshare.serviceapi.repository.RoleRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        List<String> roleNames = Arrays.asList("ROLE_ADMIN", "ROLE_USER");

        for (String roleName : roleNames) {
            if (!roleRepository.existsByName(roleName)) {
                Role role = Role.builder()
                        .name(roleName)
                        .build();
                role.setCreatedAt(LocalDateTime.now());
                role.setUpdatedAt(role.getCreatedAt());
                role.setStatus(Status.ACTIVE);
                roleRepository.save(role);
            }
        }
    }
}

