package com.todo.todo_back.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteInstance extends EntityWithId {

    @Getter
    @RequiredArgsConstructor
    public enum Fields {
        ID("id"),
        USER("user"),
        MOMENT("moment"),
        RECIPE("recipe");

        private final String databaseFieldName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Recipe recipe;

    @Column
    private Long moment;
}
