package com.todo.todo_back.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UnitUnitConversion implements EntityWithId {

    @RequiredArgsConstructor
    @Getter
    public enum Fields {
        ID("id"),
        MEASUREMENT_FROM("measurementFrom"),
        MEASUREMENT_TO("measurementTo"),
        COEFFICIENT("coefficient");

        private final String databaseFieldName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private MeasurementUnit measurementFrom;

    @ManyToOne
    private MeasurementUnit measurementTo;

    @Column
    private double coefficient;
}
