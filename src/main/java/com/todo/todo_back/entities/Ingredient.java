package com.todo.todo_back.entities;

import io.opencensus.stats.Measurement;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.checkerframework.common.aliasing.qual.Unique;

import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient extends EntityWithId {

    @RequiredArgsConstructor
    @Getter
    public enum Fields {
        ID("id"),
        NAME("name"),
        UNIT("unit"),
        LIFE_STYLES("lifeStyles"),
        CALORIES("calories"),;

        private final String databaseFieldName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Unique
    @NotNull(message = "Name must be specified!")
    @Size(min = 2, message = "Name must be at least 2 characters long!")
    private String name;

    @Column
    private Long calories;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "ingredient_life_style",
            joinColumns = @JoinColumn(name = "ingredient_id"),
            inverseJoinColumns = @JoinColumn(name = "life_style_id"))
    private Collection<LifeStyle> lifeStyles;
}
