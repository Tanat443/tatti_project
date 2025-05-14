package com.exapmle.userservice.repository;

import com.exapmle.userservice.model.ShopOwner;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ShopOwnerRepository extends R2dbcRepository<ShopOwner, UUID> {

    Mono<ShopOwner> findByUserId(UUID id);

}
