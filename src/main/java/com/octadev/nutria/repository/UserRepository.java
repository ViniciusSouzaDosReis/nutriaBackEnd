package com.octadev.nutria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.octadev.nutria.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    
}
