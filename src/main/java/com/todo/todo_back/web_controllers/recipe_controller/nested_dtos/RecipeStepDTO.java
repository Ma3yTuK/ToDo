package com.todo.todo_back.web_controllers.recipe_controller.nested_dtos;

import com.todo.todo_back.entities.RecipeStep;
import com.todo.todo_back.entities.Review;
import com.todo.todo_back.entities.UnitUnitConversion;
import com.todo.todo_back.web_controllers.ingredient_controller.IngredientDTO;
import com.todo.todo_back.web_controllers.user_controller.UserShortDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeStepDTO {

    public RecipeStepDTO(RecipeStep recipeStep) {
        id = recipeStep.getId();
        description = recipeStep.getDescription();
    }

    private Long id;

    private String description;
}
