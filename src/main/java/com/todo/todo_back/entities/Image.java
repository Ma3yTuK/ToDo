package com.todo.todo_back.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Image implements EntityWithId {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Unique
    @NotNull(message = "Location must be specified!")
    @Size(min = 2, message = "Location must be at least 2 characters long!")
    private String location;
}
