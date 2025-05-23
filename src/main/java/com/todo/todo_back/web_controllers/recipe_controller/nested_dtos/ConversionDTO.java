package com.todo.todo_back.web_controllers.recipe_controller.nested_dtos;

import com.todo.todo_back.entities.Ingredient;
import com.todo.todo_back.entities.IngredientUnitConversion;
import com.todo.todo_back.entities.MeasurementUnit;
import com.todo.todo_back.web_controllers.ingredient_controller.IngredientDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConversionDTO {

    public ConversionDTO(IngredientUnitConversion conversion) {
        id = conversion.getId();
        ingredient = new IngredientDTO(conversion.getIngredient());
        unit = conversion.getUnit();
        coefficient = conversion.getCoefficient();
    }

    private Long id;

    private IngredientDTO ingredient;

    private MeasurementUnit unit;

    private Double coefficient;
}
