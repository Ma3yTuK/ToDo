package com.todo.todo_back.specifications;

import com.todo.todo_back.entities.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    private UserSpecification() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<User> searchBy(String searchQuery) {
        return (root, query, builder) -> {
            if (searchQuery == null) {
                return builder.conjunction();
            }
            return builder.like(builder.lower(root.get(User.Fields.NAME.getDatabaseFieldName())), "%" + searchQuery.toLowerCase() + "%");
        };
    }
}
