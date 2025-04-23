package com.exapmle.userservice.mapper;

import com.exapmle.userservice.dto.ConditerDTO;
import com.exapmle.userservice.model.Conditer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConditerMapper {
Conditer toModel(ConditerDTO dto);
ConditerDTO toDTO(Conditer model);
List<Conditer> toModelList(List<ConditerDTO> dtos);
List<ConditerDTO> toDTOList(List<Conditer> models);
}
