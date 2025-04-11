package com.exapmle.gateway.api;

import com.exapmle.gateway.dto.UserCreateDTO;
import com.exapmle.gateway.dto.UserLoginDTO;
import com.exapmle.gateway.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        return new ResponseEntity<>(userService.createUser(userCreateDTO), HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO) {
        return new ResponseEntity<>(userService.authenticate(userLoginDTO), HttpStatus.OK);
    }
}
