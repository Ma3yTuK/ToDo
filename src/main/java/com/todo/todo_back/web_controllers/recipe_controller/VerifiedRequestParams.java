package com.todo.todo_back.web_controllers.recipe_controller;

import com.todo.todo_back.entities.Recipe;
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
public class VerifiedRequestParams implements PageableFilter {
    private String sortingOption = Recipe.Fields.ID.getDatabaseFieldName();
    private Integer offset = null;
    private Integer limit = null;

    @Override
    public Sort getSortingOption() {
        return Sort.by(Sort.Direction.DESC, sortingOption);
    }
}
