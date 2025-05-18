package com.todo.todo_back.entities;

public interface EntityWithId {
    Long getId();

    default boolean equals(EntityWithId o) {
        return this.getId().equals(o.getId());
    }
}
