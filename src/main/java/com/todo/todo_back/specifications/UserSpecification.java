package com.todo.todo_back.specifications;

import com.todo.todo_back.entities.user.User;
import com.todo.todo_back.entities.user.UserEntity;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    private UserSpecification() {
        throw new IllegalStateException("Utility class");
    }

    public static <T extends UserEntity> Specification<T> searchBy(String searchQuery) {
        return (root, query, builder) -> builder.like(builder.lower(root.get(User.Fields.NAME.getDatabaseFieldName())), "%" + searchQuery.toLowerCase() + "%");
    }
}
