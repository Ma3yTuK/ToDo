package com.todo.todo_back.web_controllers.user_controller;

import com.todo.todo_back.utilities.PageableFilter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestParams implements PageableFilter {
    private String searchQuery;
    private String sortingOption;
    private Integer offset;
    private Integer limit;
}
