package com.todo.todo_back.web_controllers.review_controller;

import com.todo.todo_back.entities.Review;
import com.todo.todo_back.utilities.PageableFilter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestParams implements PageableFilter {
    private String sortingOption = Review.Fields.MOMENT.getDatabaseFieldName();
    private Integer offset = null;
    private Integer limit = null;

    @Override
    public Sort getSortingOption() {
        return Sort.by(Sort.Direction.DESC, sortingOption);
    }
}
