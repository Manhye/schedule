package com.example.schedule.repository;

import com.example.schedule.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    boolean existsByEmail(String email);

    Optional<Author> findByEmail(String email);

    Author findIdByEmail(String email);
}
