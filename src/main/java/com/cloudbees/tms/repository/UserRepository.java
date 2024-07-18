package com.cloudbees.tms.repository;

import com.cloudbees.tms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<Object> findByFirstNameAndLastNameAndEmail(String firstName, String lastName, String email);
}