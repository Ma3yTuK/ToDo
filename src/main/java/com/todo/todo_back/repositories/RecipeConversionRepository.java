package com.todo.todo_back.repositories;

import com.todo.todo_back.entities.FavoriteInstance;
import com.todo.todo_back.entities.RecipeConversion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeConversionRepository extends JpaRepository<RecipeConversion, Long> {
    void deleteAllByRecipeId(Long recipeId);
}
