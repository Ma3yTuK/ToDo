package com.todo.todo_back.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteInstance implements EntityWithId {

    @Getter
    @RequiredArgsConstructor
    public enum Fields {
        ID("id"),
        USER("user"),
        RECIPE("recipe");

        private final String databaseFieldName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Recipe recipe;
}
