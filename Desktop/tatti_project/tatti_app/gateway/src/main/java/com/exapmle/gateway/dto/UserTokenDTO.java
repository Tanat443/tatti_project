package com.exapmle.gateway.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTokenDTO {
    private String token;
    private String refreshToken;
}

