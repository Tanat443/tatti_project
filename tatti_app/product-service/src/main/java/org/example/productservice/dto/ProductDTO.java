package org.example.productservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String cpfc;
    private String photo;
    private Double weight;
    private Long categoryId;
}

