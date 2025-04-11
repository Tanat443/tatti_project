package com.exapmle.userservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Table("users")
public class User {
    @Id
    private UUID id; // Уникальный ID из Keycloak
    private String email;
    private String username;
    private String role; //  // Ссылка на аватар в MinIO



}