package ru.pasteshare.serviceapi.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.pasteshare.serviceapi.model.User;
import ru.pasteshare.serviceapi.repository.UserRepository;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserInfoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserInfo loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(UserInfo::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));

    }
}
