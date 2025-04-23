package com.exapmle.userservice.repository;

import com.exapmle.userservice.model.Conditer;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ConditerRepository extends R2dbcRepository<Conditer, Long> {
}
