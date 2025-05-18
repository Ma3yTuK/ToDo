package com.todo.todo_back.repositories;

import com.todo.todo_back.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
