package com.exapmle.gateway.service;

import com.exapmle.gateway.client.KeyCloakClient;
import com.exapmle.gateway.dto.UserCreateDTO;
import com.exapmle.gateway.dto.UserDTO;
import com.exapmle.gateway.dto.UserLoginDTO;
import com.exapmle.gateway.dto.UserTokenDTO;
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
    private final WebClient.Builder webClientBuilder;
    @Value("${user.service.url}")
    private String userServiceUrl;

    public Mono<UserDTO> createUser(UserCreateDTO userCreateDTO){
        UserRepresentation userRepresentation = keyCloakClient.createUser(userCreateDTO);

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(userRepresentation.getUsername());
        userDTO.setEmail(userRepresentation.getEmail());
        userDTO.setFirstName(userRepresentation.getFirstName());
        userDTO.setLastName(userRepresentation.getLastName());

        UserLoginDTO userLoginDTO = new UserLoginDTO(userCreateDTO.getEmail(), userCreateDTO.getPassword());

        String token = keyCloakClient.signIn(userLoginDTO).getToken();

        return webClientBuilder.build()
                .post()
                .uri(userServiceUrl + "/users")
                .header("Authorization", "Bearer " + token)
                .bodyValue(userDTO)
                .retrieve()
                .bodyToMono(UserDTO.class);
    }

    public Mono<UserTokenDTO> authenticate(UserLoginDTO userLoginDTO){
        return Mono.just(keyCloakClient.signIn(userLoginDTO));
    }

}
