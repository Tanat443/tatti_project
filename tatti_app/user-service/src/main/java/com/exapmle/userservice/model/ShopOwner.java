package com.exapmle.userservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shop_owners")
public class ShopOwner {
    @Id
    private UUID id;         // UUID магазина
    private UUID userId;     // Владелец (user.id)

    private String shopName;
    private String location;
    private float rating;    // (опционально, можно позже обновлять)
    private String description;
    private String photoUrl; // логотип, если есть
    private boolean verified; // подтверждение модератором
}
