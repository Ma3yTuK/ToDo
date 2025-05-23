package com.todo.todo_back.entities;

public abstract class EntityWithId {
    abstract Long getId();

    @Override
    public boolean equals(Object o) {
        if (o instanceof EntityWithId) {
            return this.getId().equals(((EntityWithId)o).getId());
        }
        return false;
    }
}
