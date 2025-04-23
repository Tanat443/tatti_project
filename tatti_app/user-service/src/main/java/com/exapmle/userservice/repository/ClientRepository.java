package com.exapmle.userservice.repository;

import com.exapmle.userservice.model.Client;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ClientRepository extends R2dbcRepository<Client, Long> {

}
