package com.todo.todo_back.web_controllers.ingredient_controller;

import com.todo.todo_back.entities.Ingredient;
import com.todo.todo_back.entities.IngredientUnitConversion;
import com.todo.todo_back.entities.MeasurementUnit;
import com.todo.todo_back.entities.Review;
import com.todo.todo_back.web_controllers.user_controller.UserShortDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IngredientDTO {

    public IngredientDTO(Ingredient ingredient) {
        id = ingredient.getId();
        name = ingredient.getName();
        calories = ingredient.getCalories();
    }

    private Long id;
    private Long calories;
    private String name;
}
