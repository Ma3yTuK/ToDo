package com.todo.todo_back.specifications;

import java.time.ZonedDateTime;

import org.springframework.data.jpa.domain.Specification;

import com.todo.todo_back.entities.Task;
import com.todo.todo_back.entities.User;

public class TaskSpecification {

    private TaskSpecification() {
        throw new IllegalStateException("Utility class");
    }
    
    public static Specification<Task> isStatus(Boolean value) {
        return (root, query, builder) -> builder.equal(root.get(Task.Fields.STATUS.getDatabaseFieldName()), value);
    }

    public static Specification<Task> dueGreaterThan(ZonedDateTime value) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get(Task.Fields.DUE.getDatabaseFieldName()), value);
    }

    public static Specification<Task> dueLessThan(ZonedDateTime value) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get(Task.Fields.DUE.getDatabaseFieldName()), value);
    }

    public static Specification<Task> titleLike(String titleLike) {
        return (root, query, builder) -> builder.like(root.get(Task.Fields.TITLE.getDatabaseFieldName()), "%" + titleLike + "%");
    }

    public static Specification<Task> userEqual(User user) {
        return (root, query, builder) -> builder.equal(root.get(Task.Fields.USER.getDatabaseFieldName()), user);
    }

}
