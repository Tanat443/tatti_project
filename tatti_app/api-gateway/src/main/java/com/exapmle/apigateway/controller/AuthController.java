package com.exapmle.apigateway.controller;

import com.exapmle.apigateway.dto.UserCreateDTO;
import com.exapmle.apigateway.dto.UserDTO;
import com.exapmle.apigateway.dto.UserLoginDTO;
import com.exapmle.apigateway.dto.UserTokenDTO;
import com.exapmle.apigateway.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping(value = "/create")
    public Mono<ResponseEntity<UserTokenDTO>> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        return userService.createUser(userCreateDTO)
                .map(token -> ResponseEntity.status(HttpStatus.CREATED).body(token));
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<UserTokenDTO>> login(@RequestBody UserLoginDTO userLoginDTO) {
        return userService.authenticate(userLoginDTO)
                .map(token -> ResponseEntity.ok(token));
    }
}
