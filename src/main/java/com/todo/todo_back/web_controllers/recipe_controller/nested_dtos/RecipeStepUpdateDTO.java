package com.todo.todo_back.web_controllers.recipe_controller.nested_dtos;

import com.todo.todo_back.entities.RecipeIngredient;
import com.todo.todo_back.entities.RecipeStep;
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
public class RecipeStepUpdateDTO {

    private Long id = null;

    private String description = null;
}
