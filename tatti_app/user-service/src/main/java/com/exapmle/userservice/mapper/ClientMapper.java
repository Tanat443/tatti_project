package com.exapmle.userservice.mapper;

import com.exapmle.userservice.dto.ClientDTO;
import com.exapmle.userservice.model.Client;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client toModel(ClientDTO dto);
    ClientDTO toDTO(Client model);
    List<Client> toModelList(List<ClientDTO> dtos);
    List<ClientDTO> toDTOList(List<Client> models);
}
