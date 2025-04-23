package com.exapmle.userservice.dto;

import lombok.Data;

import javax.xml.stream.Location;
import java.time.LocalDateTime;

@Data
public class ConditerDTO {
    private Long id;
    private String email;
    private String fullname;
    private float balance;
    private String phone;
    private LocalDateTime created_at;
    private Boolean is_store;
    private Location location;
    private float rating;
    private String photo;
    private String avatar;
}
