package org.example.productservice.mapper;

import org.example.productservice.model.Product;
import org.example.productservice.dto.ProductDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "category.id", target = "categoryId")
    ProductDTO toDTO(Product product);

    @Mapping(source = "categoryId", target = "category.id")
    Product toEntity(ProductDTO productDTO);
}
