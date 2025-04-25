package org.example.productservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("products")
public class Product {

    @Id
    private Long id;

    private String name;
    private String description;
    private Double price;
    private String cpfc;
    private String photo;
    private Double weight;

    @Column("category_id")
    private Long categoryId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
