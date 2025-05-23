package com.todo.todo_back.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.checkerframework.common.aliasing.qual.Unique;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MeasurementUnit extends EntityWithId {

    @RequiredArgsConstructor
    @Getter
    public enum Fields {
        ID("id"),
        NAME("name");

        private final String databaseFieldName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Unique
    @NotNull(message = "Name must be specified!")
    @Size(min = 2, message = "Name must be at least 2 characters long!")
    private String name;
}
