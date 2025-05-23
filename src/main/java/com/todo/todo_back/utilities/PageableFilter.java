package com.todo.todo_back.utilities;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface PageableFilter {
    Integer getOffset();
    Integer getLimit();
    Sort getSortingOption();

    default Pageable getPageable() {
        if (getOffset() == null || getLimit() == null) {
            return Pageable.unpaged(getSortingOption());
        }

        return PageRequest.of(getOffset(), getLimit(), getSortingOption());
    }
}
