package com.todo.todo_back.web_controllers.recipe_controller.nested_dtos;

import com.todo.todo_back.entities.RecipeConversion;
import com.todo.todo_back.entities.IngredientUnitConversion;
import com.todo.todo_back.web_controllers.ingredient_controller.IngredientDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.units.qual.C;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeConversionDTO {

    public RecipeConversionDTO(RecipeConversion recipeConversion) {
        id = recipeConversion.getId();
        amount = recipeConversion.getAmount();
        ingredientUnitConversion = new ConversionDTO(recipeConversion.getConversion());
    }

    private Long id;

    private Long amount;

    private ConversionDTO ingredientUnitConversion;
}
