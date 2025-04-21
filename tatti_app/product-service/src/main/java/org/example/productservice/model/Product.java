package org.example.productservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Double price;
    private String cpfc;
    private String photo;
    private Double weight;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
