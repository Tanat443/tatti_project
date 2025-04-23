package com.exapmle.userservice.service;

import com.exapmle.userservice.dto.ConditerDTO;
import com.exapmle.userservice.mapper.ConditerMapper;
import com.exapmle.userservice.model.Client;
import com.exapmle.userservice.model.Conditer;
import com.exapmle.userservice.repository.ConditerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class ConditerService {
    private final ConditerRepository repository;
    private final ConditerMapper mapper;

    public Flux<ConditerDTO> getAllConditers() {
        return repository.findAll().map(mapper::toDTO);
    }

    public Mono<ConditerDTO> getConditerById(Long id) {
        return repository.findById(id).map(mapper::toDTO);
    }

    public Mono<Void> deleteConditerById(Long id) {
        return repository.deleteById(id);
    }

    public Mono<ConditerDTO> saveConditer(ConditerDTO dto) {
        Conditer conditer = mapper.toModel(dto);
        return repository.save(conditer).map(mapper::toDTO);
    }

    public Mono<ConditerDTO> updateConditer(Long id, ConditerDTO dto) {
        return repository.findById(id).flatMap(existing -> {
            Conditer updated = mapper.toModel(dto);
            updated.setId(id);
            return repository.save(updated).map(mapper::toDTO);
        });
    }
}
