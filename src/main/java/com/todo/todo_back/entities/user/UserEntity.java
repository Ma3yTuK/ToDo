package com.todo.todo_back.entities.user;

import com.todo.todo_back.entities.EntityWithId;
import com.todo.todo_back.entities.Image;

public interface UserEntity extends EntityWithId {
    String getName();
    String getEmail();
    Boolean getIsVerified();
    Image getImage();
}
