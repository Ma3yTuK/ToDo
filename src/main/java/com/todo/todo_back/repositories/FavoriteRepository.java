package com.todo.todo_back.repositories;

import com.todo.todo_back.entities.FavoriteInstance;
import com.todo.todo_back.entities.Recipe;
import com.todo.todo_back.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<FavoriteInstance, Long> {
    Optional<FavoriteInstance> findByRecipeAndUser(Recipe recipe, User user);
}
