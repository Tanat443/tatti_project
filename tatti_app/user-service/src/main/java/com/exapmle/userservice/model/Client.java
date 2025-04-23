package com.exapmle.userservice.model;
import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String fullname;
    private String phone;
    private float bonus_balance;
    private LocalDateTime created_at;

    @PrePersist
    public void onCreate() {
        this.created_at = LocalDateTime.now();
    }
}
