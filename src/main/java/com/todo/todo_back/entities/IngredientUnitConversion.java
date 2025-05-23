package com.todo.todo_back.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IngredientUnitConversion extends EntityWithId {

    @RequiredArgsConstructor
    @Getter
    public enum Fields {
        ID("id"),
        INGREDIENT("ingredient"),
        UNIT("unit"),
        COEFFICIENT("coefficient");

        private final String databaseFieldName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Ingredient ingredient;

    @ManyToOne
    private MeasurementUnit unit;

    @Column
    private double coefficient;
}
