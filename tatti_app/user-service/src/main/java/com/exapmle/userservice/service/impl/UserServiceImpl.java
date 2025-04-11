package com.exapmle.userservice.service.impl;

import com.exapmle.userservice.dto.UserDTO;
import com.exapmle.userservice.model.User;
import com.exapmle.userservice.repository.UserRepository;
import com.exapmle.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    @Override
    public Mono<Object> registerUser(UserDTO userDTO, Jwt jwt) {
        UUID keycloakId = UUID.fromString(jwt.getSubject());
        User user = new User();
        user.setId(keycloakId); // Новый уникальный ID
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail()); // Email необязателен
        user.setRole(userDTO.getRole()); // Роль по умолчанию

        return userRepository.existsById(keycloakId)
                .flatMap(exists -> {
                    if (exists) {
                        log.warn("User already exists: {}", keycloakId);
                        return Mono.error(new RuntimeException("User already registered"));
                    } else {
                        return userRepository.save(user)
                                .map(savedUser -> new UserDTO(
                                        savedUser.getEmail(),
                                        savedUser.getUsername(),
                                        savedUser.getRole(),
                                        null
                                ))
                                .doOnSuccess(u -> log.info("User registered: {}", u))
                                .doOnError(e -> log.error("Failed to register user: {}", e.getMessage()));
                    }
                });
    }

    @Override
    public Mono<UserDTO> getCurrentUser(UUID keycloakId) {
        return userRepository.findById(keycloakId)
                .map(user -> new UserDTO(
                        user.getEmail(),
                        user.getUsername(),
                        user.getRole(),
                        null))
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")));
    }
}