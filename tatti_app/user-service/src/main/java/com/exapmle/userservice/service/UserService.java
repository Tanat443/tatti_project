package com.exapmle.userservice.service;

import com.exapmle.userservice.dto.UserDTO;

import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;

import java.util.UUID;


public interface UserService {
    Mono<Object> registerUser(UserDTO userDTO, Jwt jwt);
    Mono<UserDTO> getCurrentUser(UUID keycloakId);
}
