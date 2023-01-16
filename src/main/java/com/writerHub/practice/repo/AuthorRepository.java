package com.writerHub.practice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.writerHub.practice.models.Author;

public interface AuthorRepository extends JpaRepository<Author,Long> {
}
