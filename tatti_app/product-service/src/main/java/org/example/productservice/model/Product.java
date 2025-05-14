package org.example.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    private UUID id;

    private String name;
    private String description;
    private Double price;

    private String cpfc;           // калории/пит. ценность
    private String photo;
    private Double weight;

    @Column("category_id")
    private UUID categoryId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Column("created_by")
    private UUID createdBy; // ID пользователя, если это кондитер/владелец магазина

    @Column("creator_role")
    private String creatorRole; // ADMIN, CONFECTIONER, SHOP_OWNER

    @Column("is_customizable")
    private boolean customizable; // для кастомных тортов

    @Column("status")
    private String status; // PENDING, APPROVED — для модерации
}
