package com.todo.todo_back.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeStep extends EntityWithId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Unique
    @NotNull(message = "Description must be specified!")
    @Size(min = 2, message = "Description must be at least 2 characters long!")
    private String description;

    @NotNull
    private Long index;

    @ManyToOne
    private Recipe recipe;

    @ManyToOne
    private Image image;
}
