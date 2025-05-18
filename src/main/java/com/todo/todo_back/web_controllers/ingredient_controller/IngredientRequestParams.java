package com.todo.todo_back.web_controllers.ingredient_controller;

import com.todo.todo_back.entities.Ingredient;
import com.todo.todo_back.utilities.PageableFilter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IngredientRequestParams implements PageableFilter {
    private String sortingOption = Ingredient.Fields.NAME.getDatabaseFieldName();
    private Integer offset = null;
    private Integer limit = null;
}
