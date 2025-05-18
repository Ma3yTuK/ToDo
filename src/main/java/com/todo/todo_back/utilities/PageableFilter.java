package com.todo.todo_back.utilities;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface PageableFilter {
    Integer getOffset();
    Integer getLimit();
    String getSortingOption();

    default Pageable getPageable() {
        Sort sort = Sort.by(getSortingOption());

        if (getOffset() == null || getLimit() == null) {
            return Pageable.unpaged(sort);
        }

        return PageRequest.of(getOffset(), getLimit(), sort);
    }
}
