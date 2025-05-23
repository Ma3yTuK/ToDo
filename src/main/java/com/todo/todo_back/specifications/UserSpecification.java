package com.todo.todo_back.specifications;

import com.todo.todo_back.entities.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {

    private UserSpecification() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<User> searchBy(String searchQuery, Boolean isVerified) {
        return (root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (searchQuery != null) {
                predicates.add(builder.like(builder.lower(root.get(User.Fields.NAME.getDatabaseFieldName())), "%" + searchQuery.toLowerCase() + "%"));
            }
            if (isVerified != null) {
                predicates.add(builder.equal(root.get(User.Fields.IS_VERIFIED.getDatabaseFieldName()), isVerified));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
