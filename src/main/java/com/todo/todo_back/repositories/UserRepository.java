package com.todo.todo_back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.todo_back.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
