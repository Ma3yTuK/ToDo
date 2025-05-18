package com.todo.todo_back.entities;

public interface EntityWithId {
    public Long getId();

    public default boolean equals(EntityWithId o) {
        return this.getId().equals(o.getId());
    }
}
