package com.todo.todo_back.utilities;

import lombok.Getter;

@Getter
public enum Authorities {
    MODERATE(3L),
    PREMIUM(4L);
    
    Authorities(Long id) {
        this.id = id;
    }
    
    final private Long id;
}
