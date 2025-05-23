package com.todo.todo_back.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.checkerframework.checker.units.qual.C;
import org.checkerframework.common.aliasing.qual.Unique;

import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Recipe extends EntityWithId {

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
        IS_VERIFIED("isVerified"),
        LIKES("likes");

        private final String databaseFieldName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column
    private Boolean isVerified;

    @ManyToOne
    private User user;

    @ManyToOne
    private Image image;

    @NotNull
    private Long weight;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "recipe", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private Collection<RecipeStep> steps;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "recipe", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private Collection<RecipeConversion> ingredients;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.REMOVE)
    private Collection<FavoriteInstance> likes;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.REMOVE)
    private Collection<Review> reviews;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Collection<Category> categories;
}
