package com.exapmle.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUniqueDTO {
    private UUID id;
    private String name;
    private String description;
    private Double price;
    private String cpfc;
    private String photo;
    private Double weight;
    private UUID categoryId;
    private UUID createdBy;
    private String creatorRole;
    private boolean customizable;
    private String status;
}
