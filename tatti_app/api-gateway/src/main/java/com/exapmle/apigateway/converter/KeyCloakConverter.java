package com.exapmle.apigateway.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

public class KeyCloakConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {
    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt source) {
        Collection<GrantedAuthority> authorities = extractAuthorities(source);
        return Mono.just(new JwtAuthenticationToken(source, authorities));
    }
    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        List<String> roles = new ArrayList<>();

        // Realm roles
        Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");
        if (realmAccess != null && !realmAccess.isEmpty()) {
            List<String> realmRoles = (List<String>) realmAccess.get("roles");
            if (realmRoles != null) {
                roles.addAll(realmRoles);
            }
        }

        // Client roles (например, для tatti-app1)
        Map<String, Object> resourceAccess = (Map<String, Object>) jwt.getClaims().get("resource_access");
        if (resourceAccess != null && resourceAccess.containsKey("tatti-app1")) {
            Map<String, Object> clientAccess = (Map<String, Object>) resourceAccess.get("tatti-app1");
            List<String> clientRoles = (List<String>) clientAccess.get("roles");
            if (clientRoles != null) {
                roles.addAll(clientRoles);
            }
        }

        if (roles.isEmpty()) {
            return Collections.emptyList();
        }

        return roles.stream()
                .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role) // Добавляем префикс только если его нет
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
