package com.todo.todo_back.web_controllers.recipe_controller.nested_dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeIngredientUpdateDTO {

    private Long id = null;

    private Float amount = null;

    private Long ingredientId = null;

    private Long conversionId = null;
}