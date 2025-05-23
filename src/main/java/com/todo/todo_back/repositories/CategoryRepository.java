package com.todo.todo_back.repositories;

import com.todo.todo_back.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
