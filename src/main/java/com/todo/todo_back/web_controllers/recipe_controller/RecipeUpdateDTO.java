package com.todo.todo_back.web_controllers.recipe_controller;

import com.todo.todo_back.entities.Image;
import com.todo.todo_back.entities.Ingredient;
import com.todo.todo_back.entities.RecipeIngredient;
import com.todo.todo_back.entities.RecipeStep;
import com.todo.todo_back.web_controllers.recipe_controller.nested_dtos.RecipeIngredientUpdateDTO;
import com.todo.todo_back.web_controllers.recipe_controller.nested_dtos.RecipeStepUpdateDTO;
import com.todo.todo_back.web_controllers.user_controller.UserShortDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeUpdateDTO {

    private String name = null;

    private String description = null;

    private Collection<RecipeStepUpdateDTO> steps = Collections.emptyList();

    private Collection<RecipeIngredientUpdateDTO> ingredients = Collections.emptyList();

    private Long imageId;
}
