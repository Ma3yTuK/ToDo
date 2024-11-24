package com.todo.todo_back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.todo_back.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Iterable<Task> findByUserId(Long userId);
}
