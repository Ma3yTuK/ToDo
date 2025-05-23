package com.todo.todo_back.repositories;

import com.todo.todo_back.entities.FavoriteInstance;
import com.todo.todo_back.entities.Recipe;
import com.todo.todo_back.entities.RecipeStep;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeStepRepository extends JpaRepository<RecipeStep, Long> {
    void deleteAllByRecipeId(Long recipeId);
}
