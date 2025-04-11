package com.exapmle.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;
@Component
@Slf4j
public class AuthenticationFilter implements GatewayFilterFactory<AuthenticationFilter.Config> {

    private final WebClient webClient;

    @Value("${keycloak.url}")
    private String keycloakUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    public AuthenticationFilter(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (token == null || !token.startsWith("Bearer ")) {
                return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization header"));
            }

            token = token.substring(7);

            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("token", token);
            formData.add("client_id", clientId);
            formData.add("client_secret", clientSecret);

            return webClient.post()
                    .uri(keycloakUrl + "/realms/" + realm + "/protocol/openid-connect/token/introspect")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response ->
                            Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token introspection failed"))
                    )
                    .bodyToMono(Map.class)
                    .doOnNext(response -> log.debug("Full introspection response: {}", response)) // Логируем полный ответ
                    .flatMap(response -> {
                        Object activeObj = response.get("active");
                        log.debug("Raw 'active' value: {}", activeObj);
                        boolean isActive = false;
                        if (activeObj instanceof Boolean) {
                            isActive = (Boolean) activeObj;
                        } else if (activeObj instanceof String) {
                            isActive = Boolean.parseBoolean((String) activeObj);
                        }
                        if (isActive) {
                            log.debug("Token is valid");
                            return chain.filter(exchange);
                        } else {
                            log.warn("Invalid or inactive token: active={}", activeObj);
                            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token"));
                        }
                    })
                    .onErrorResume(e -> {
                        log.error("Error during token introspection: {}", e.getMessage());
                        return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token validation failed", e));
                    });
        };
    }

    @Override
    public Config newConfig() {
        return new Config();
    }

    @Override
    public Class<Config> getConfigClass() {
        return Config.class;
    }

    public static class Config {
        // Здесь можно добавить параметры конфигурации, если нужно
    }
}