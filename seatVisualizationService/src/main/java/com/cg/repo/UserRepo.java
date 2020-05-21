package com.cg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.model.UserEntity;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {

}

