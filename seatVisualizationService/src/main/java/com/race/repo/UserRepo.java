package com.race.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.race.entity.UserEntity;

/**
 * Fetching the Database results based on customized queries and convert into
 * entities.
 * 
 * @author praterai
 *
 */
@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {

}
