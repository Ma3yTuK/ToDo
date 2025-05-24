package com.todo.todo_back.web_controllers.recipe_controller;

import com.todo.todo_back.entities.Image;
import com.todo.todo_back.entities.Ingredient;
import com.todo.todo_back.entities.Recipe;
import com.todo.todo_back.entities.RecipeStep;
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
public class RecipeShortDTO {

    private Long id;

    private String name;

    private Long weight;

    private Long calories;

    private Boolean isVerified;

    private Float rating;

    private Boolean isFavorite;

    private Boolean isPremium;

    private Image image;
}
