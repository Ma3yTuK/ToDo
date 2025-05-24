package com.todo.todo_back.web_controllers.recipe_controller;

import com.todo.todo_back.entities.Recipe;
import com.todo.todo_back.utilities.PageableFilter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeRequestParams implements PageableFilter {
    private String searchQuery = null;
    private String sortingOption = Recipe.Fields.ID.getDatabaseFieldName();
    private Boolean sortingAscending = false;
    private Collection<Long> userIds = Collections.emptyList();
    private Collection<Long> categoryIds = Collections.emptyList();
    private Collection<Long> lifeStyleIds = Collections.emptyList();
    private Collection<Long> ingredientIds = Collections.emptyList();
    private Integer offset = null;
    private Integer limit = null;

    @Override
    public Sort getSortingOption() {
        return Sort.by(sortingAscending ? Sort.Direction.ASC : Sort.Direction.DESC, sortingOption);
    }
}