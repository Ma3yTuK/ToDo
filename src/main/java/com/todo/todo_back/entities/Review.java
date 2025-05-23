package com.todo.todo_back.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.checkerframework.common.aliasing.qual.Unique;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review extends EntityWithId {

    @RequiredArgsConstructor
    @Getter
    public enum Fields {
        ID("id"),
        RATING("rating"),
        USER("user"),
        RECIPE("recipe"),
        MOMENT("moment"),
        COMMENT("comment");

        private final String databaseFieldName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private Long rating;

    @ManyToOne
    private User user;

    @ManyToOne
    @NotNull
    private Recipe recipe;

    @Column
    private Long moment;

    @NotNull(message = "Comment must be specified!")
    @Size(min = 2, message = "Comment must be at least 2 characters long!")
    private String comment;
}
