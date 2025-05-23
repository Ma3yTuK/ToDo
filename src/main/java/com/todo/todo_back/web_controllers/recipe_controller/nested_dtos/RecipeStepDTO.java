package com.todo.todo_back.web_controllers.recipe_controller.nested_dtos;

import com.todo.todo_back.entities.Image;
import com.todo.todo_back.entities.RecipeStep;
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
        index = recipeStep.getIndex();
        image = recipeStep.getImage();
    }

    private Long id;

    private Long index;

    private String description;

    private Image image;
}
