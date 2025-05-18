package com.todo.todo_back.repositories;

import com.todo.todo_back.entities.Ingredient;
import com.todo.todo_back.entities.MeasurementUnit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
