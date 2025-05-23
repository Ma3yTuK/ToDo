package com.todo.todo_back.web_controllers.recipe_controller.nested_dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeConversionUpdateDTO {

    private Long amount = null;

    private Long conversionId = null;
}