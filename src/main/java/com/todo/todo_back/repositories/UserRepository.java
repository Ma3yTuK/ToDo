package com.todo.todo_back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.todo.todo_back.entities.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String email);
}
