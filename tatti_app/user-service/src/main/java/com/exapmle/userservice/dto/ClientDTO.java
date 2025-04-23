package com.exapmle.userservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClientDTO {
    private Long id;
    private String email;
    private String fullname;
    private String phone;
    private float bonus_balance;
    private LocalDateTime created_at;
}
