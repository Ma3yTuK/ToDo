package com.todo.todo_back.web_controllers.review_controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewUpdateDTO {
    private Long rating = null;
    private Long recipeId = null;
    private String comment = null;
}
