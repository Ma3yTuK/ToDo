package com.todo.todo_back.web_controllers.recipe_controller.nested_dtos;

import com.todo.todo_back.entities.Image;
import com.todo.todo_back.entities.RecipeIngredient;
import com.todo.todo_back.entities.RecipeStep;
import com.todo.todo_back.entities.UnitUnitConversion;
import com.todo.todo_back.web_controllers.ingredient_controller.IngredientDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeIngredientDTO {

    public RecipeIngredientDTO(RecipeIngredient recipeIngredient) {
        id = recipeIngredient.getId();
        amount = recipeIngredient.getAmount();
        ingredient = new IngredientDTO(recipeIngredient.getIngredient());
        ingredientConversion = recipeIngredient.getConversion();
    }

    private Long id;

    private Float amount;

    private IngredientDTO ingredient;

    private UnitUnitConversion ingredientConversion;
}
