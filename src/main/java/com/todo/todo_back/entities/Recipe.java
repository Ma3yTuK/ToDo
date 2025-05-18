package com.todo.todo_back.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.checkerframework.common.aliasing.qual.Unique;

import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {

    @Getter
    @RequiredArgsConstructor
    public enum Fields {
        ID("id"),
        NAME("name"),
        DESCRIPTION("description"),
        IS_PREMIUM("isPremium"),
        USER("user"),
        IMAGE("image"),
        STEPS("steps"),
        INGREDIENTS("ingredients"),
        CATEGORIES("categories"),
        LIKES("likes");

        private final String databaseFieldName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Name must be specified!")
    @Size(min = 2, message = "Name must be at least 2 characters long!")
    private String name;

    @Unique
    @NotNull(message = "Description must be specified!")
    @Size(min = 2, message = "Description must be at least 2 characters long!")
    private String description;

    @Column
    private Boolean isPremium;

    @ManyToOne
    private User user;

    @ManyToOne
    private Image image;

    @OneToMany
    private Collection<RecipeStep> steps;

    @OneToMany
    private Collection<RecipeIngredient> ingredients;

    @ManyToMany
    private Collection<Category> categories;

    @ManyToMany
    @JoinTable(
            name = "feature_instance",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Collection<User> likes;
}
