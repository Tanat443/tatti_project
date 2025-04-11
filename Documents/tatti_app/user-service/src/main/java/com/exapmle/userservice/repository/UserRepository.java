package com.exapmle.userservice.repository;

import com.exapmle.userservice.model.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepository extends R2dbcRepository<User, UUID> {
    Mono<User> findById(UUID keycloakId);
    Mono<Boolean> existsById(UUID keycloakId);
    Mono<User> findByEmail(String email);
}
