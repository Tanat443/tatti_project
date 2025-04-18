package com.exapmle.userservice.controller;

import com.exapmle.userservice.dto.UserDTO;
import com.exapmle.userservice.model.User;
import com.exapmle.userservice.repository.UserRepository;
import com.exapmle.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Object> register(@RequestBody UserDTO userDTO, @AuthenticationPrincipal Jwt jwt) {
        return userService.registerUser(userDTO, jwt);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public Mono<UserDTO> getCurrentUser(@AuthenticationPrincipal Jwt jwt) {
        UUID keycloakId = UUID.fromString(jwt.getSubject());
        return userService.getCurrentUser(keycloakId);
    }

    @GetMapping()
    public String getHello() {
        return "Hello world";
    }
}
