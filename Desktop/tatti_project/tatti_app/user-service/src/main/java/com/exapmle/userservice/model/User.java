package com.exapmle.userservice.model;

import com.exapmle.userservice.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "t_users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String fullName;

    @Enumerated(EnumType.STRING)
    private UserRole role; // CUSTOMER, BAKER, etc.
    private String phone;
    private String location;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
