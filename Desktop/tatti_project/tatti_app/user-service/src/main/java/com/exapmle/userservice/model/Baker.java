package com.exapmle.userservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_bakers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Baker {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    private Boolean isAvailable = true;
    private Double rating;
    private Boolean store_status;

    @CreationTimestamp
    private LocalDateTime created_at;

}
