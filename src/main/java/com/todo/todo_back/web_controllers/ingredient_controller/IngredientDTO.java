package com.todo.todo_back.web_controllers.ingredient_controller;

import com.todo.todo_back.entities.Ingredient;
import com.todo.todo_back.entities.MeasurementUnit;
import com.todo.todo_back.entities.Review;
import com.todo.todo_back.web_controllers.user_controller.UserShortDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IngredientDTO {

    public IngredientDTO(Ingredient ingredient) {
        id = ingredient.getId();
        name = ingredient.getName();
        unit = ingredient.getUnit();
    }

    private Long id;
    private String name;
    private MeasurementUnit unit;
}
