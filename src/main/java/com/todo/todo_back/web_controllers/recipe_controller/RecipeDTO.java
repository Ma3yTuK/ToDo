package com.todo.todo_back.web_controllers.recipe_controller;

import com.todo.todo_back.entities.*;
import com.todo.todo_back.web_controllers.recipe_controller.nested_dtos.RecipeIngredientDTO;
import com.todo.todo_back.web_controllers.recipe_controller.nested_dtos.RecipeStepDTO;
import com.todo.todo_back.web_controllers.user_controller.UserShortDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDTO {

    private Long id;

    private String name;

    private Long calories;

    private Float rating;

    private String description;

    private Boolean isFavorite;

    private Boolean isPremium;

    private UserShortDTO user;

    private Collection<RecipeStepDTO> steps;

    private Collection<RecipeIngredientDTO> ingredients;

    private Image image;
}
