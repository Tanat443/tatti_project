package com.exapmle.userservice.service;

import com.exapmle.userservice.dto.ClientDTO;
import com.exapmle.userservice.mapper.ClientMapper;
import com.exapmle.userservice.model.Client;
import com.exapmle.userservice.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository repository;
    private final ClientMapper mapper;

    public Mono<ClientDTO> saveClient(ClientDTO dto) {
        Client client = mapper.toModel(dto); // Исправлено: преобразуем DTO в модель
        return repository.save(client).map(mapper::toDTO); // Исправлено: возвращаем DTO
    }

    public Flux<ClientDTO> getAllClients() {
        return repository.findAll().map(mapper::toDTO);
    }

    public Mono<ClientDTO> getClientById(Long id) {
        return repository.findById(id).map(mapper::toDTO);
    }

    public Mono<Void> deleteClientById(Long id) {
        return repository.deleteById(id);
    }

    public Mono<ClientDTO> updateClient(Long id, ClientDTO dto) {
        return repository.findById(id).flatMap(existing -> {
            Client updated = mapper.toModel(dto);
            updated.setId(id);
            return repository.save(updated).map(mapper::toDTO);
        });
    }
}
