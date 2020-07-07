package com.pixel.stupidbrain.repository;

import com.pixel.stupidbrain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String nickname);

    boolean existsByUsername(String nickname);

    boolean existsByEmail(String email);
}
