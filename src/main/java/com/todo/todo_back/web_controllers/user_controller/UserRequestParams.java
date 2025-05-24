package com.todo.todo_back.web_controllers.user_controller;

import com.todo.todo_back.entities.User;
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
public class UserRequestParams implements PageableFilter {
    private Boolean isVerified = null;
    private String searchQuery = null;
    private String sortingOption = User.Fields.ID.getDatabaseFieldName();
    private Boolean sortingAscending = false;
    private Integer offset = null;
    private Integer limit = null;

    @Override
    public Sort getSortingOption() {
        return Sort.by(sortingAscending ? Sort.Direction.ASC : Sort.Direction.DESC, sortingOption);
    }
}
