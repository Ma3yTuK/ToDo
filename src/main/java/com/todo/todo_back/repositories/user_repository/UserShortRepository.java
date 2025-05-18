package com.todo.todo_back.repositories.user_repository;

import com.todo.todo_back.entities.user.User;
import com.todo.todo_back.entities.user.UserShort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserShortRepository extends JpaRepository<UserShort, Long>, JpaSpecificationExecutor<UserShort> {
}
