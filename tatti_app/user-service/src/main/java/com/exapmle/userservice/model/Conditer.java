package com.exapmle.userservice.model;

import io.netty.channel.local.LocalAddress;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import javax.xml.stream.Location;
import java.time.LocalDateTime;

@Data
@Entity(name = "conditers")
public class Conditer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
