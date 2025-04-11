package com.exapmle.gateway.converter;


import com.exapmle.gateway.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Slf4j
public final class UserUtils {

    public static Jwt getCurrentUserJwt(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof JwtAuthenticationToken){
            return ((JwtAuthenticationToken) authentication).getToken();
        }
        log.warn("Couldn't get user data");
        return null;
    }

    public static String getCurrentUserName(){
        Jwt jwt = getCurrentUserJwt();
        if(jwt!=null){
            return jwt.getClaimAsString("preferred_username");
        }
        log.warn("Couldn't get user data");
        return null;
    }

    public static UserDTO getCurrentUser(){
        Jwt jwt = getCurrentUserJwt();
        if(jwt!=null){
            return UserDTO.builder()
                    .email(jwt.getClaimAsString("email"))
                    .username(jwt.getClaimAsString("preferred_username"))
                    .firstName(jwt.getClaimAsString("given_name"))
                    .lastName(jwt.getClaimAsString("family_name"))
                    .build();
        }
        log.warn("Couldn't get user data");
        return null;
    }

}