package com.exapmle.apigateway.service;

import com.exapmle.apigateway.client.KeyCloakClient;
import com.exapmle.apigateway.dto.UserCreateDTO;
import com.exapmle.apigateway.dto.UserDTO;
import com.exapmle.apigateway.dto.UserLoginDTO;
import com.exapmle.apigateway.dto.UserTokenDTO;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    private final KeyCloakClient keyCloakClient;

    public Mono<UserTokenDTO> createUser(UserCreateDTO userCreateDTO) {
        return Mono.fromCallable(() -> keyCloakClient.createUser(userCreateDTO))
                .flatMap(userRepresentation -> {
                    UserLoginDTO userLoginDTO = new UserLoginDTO(userCreateDTO.getEmail(), userCreateDTO.getPassword());
                    return Mono.fromCallable(() -> keyCloakClient.signIn(userLoginDTO));
                })
                .onErrorMap(e -> new RuntimeException("Failed to create user in Keycloak", e));
    }

    public Mono<UserTokenDTO> authenticate(UserLoginDTO userLoginDTO) {
        return Mono.fromCallable(() -> keyCloakClient.signIn(userLoginDTO))
                .onErrorMap(e -> new RuntimeException("Failed to authenticate", e));
    }
}