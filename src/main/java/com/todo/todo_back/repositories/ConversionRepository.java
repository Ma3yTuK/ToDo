package com.todo.todo_back.repositories;

import com.todo.todo_back.entities.Ingredient;
import com.todo.todo_back.entities.MeasurementUnit;
import com.todo.todo_back.entities.IngredientUnitConversion;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ConversionRepository extends JpaRepository<IngredientUnitConversion, Long>  {
    Collection<IngredientUnitConversion> findAllByIngredient(Ingredient ingredient, Sort sort);
}
