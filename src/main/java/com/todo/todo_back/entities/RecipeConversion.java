package com.todo.todo_back.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeConversion extends EntityWithId {

    @RequiredArgsConstructor
    @Getter
    public enum Fields {
        ID("id"),
        AMOUNT("amount"),
        RECIPE("recipe"),
        INDEX("index"),
        CONVERSION("conversion");

        private final String databaseFieldName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long amount;

    @ManyToOne
    private Recipe recipe;

    @ManyToOne
    @NotNull
    private IngredientUnitConversion conversion;
}
