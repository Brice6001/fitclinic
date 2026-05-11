package com.fitclinic.fitclinic.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.fitclinic.fitclinic.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
}