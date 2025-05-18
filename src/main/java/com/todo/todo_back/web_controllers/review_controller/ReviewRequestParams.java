package com.todo.todo_back.web_controllers.review_controller;

import com.todo.todo_back.entities.Review;
import com.todo.todo_back.utilities.PageableFilter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestParams implements PageableFilter {
    private String sortingOption = Review.Fields.DATE.toString();
    private Integer offset = null;
    private Integer limit = null;
}
