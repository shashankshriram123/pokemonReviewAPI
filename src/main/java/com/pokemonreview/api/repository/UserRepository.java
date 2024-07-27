package com.pokemonreview.api.repository;

import com.pokemonreview.api.model.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UsersEntity, Integer> {
    Optional<UsersEntity> findByUsername(String username);
    Boolean existsByUsername(String username);

}
