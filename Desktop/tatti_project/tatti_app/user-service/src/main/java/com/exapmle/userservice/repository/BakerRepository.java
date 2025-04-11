package com.exapmle.userservice.repository;

import com.exapmle.userservice.model.Baker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BakerRepository extends JpaRepository<Baker, Long> {

}
