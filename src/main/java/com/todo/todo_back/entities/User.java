package com.todo.todo_back.entities;

import java.util.Collection;
import java.util.Collections;

import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.common.aliasing.qual.Unique;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements EntityWithId {

    @RequiredArgsConstructor
    @Getter
    public enum Fields {
        ID("id"),
        NAME("name"),
        EMAIL("email"),
        HAS_PREMIUM("hasPremium"),
        IS_VERIFIED("isVerified"),
        IMAGE("image"),
        AUTHORITIES("authorities");

        private final String databaseFieldName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Username must be specified!")
    @Size(min = 2, message = "Username must be at least 2 characters long!")
    private String name;

    @Unique
    @NotNull(message = "Password must be specified!")
    @Size(min = 2, message = "Email must be at least 2 characters long!")
    private String email;

    @Column
    private Boolean isVerified;

    @ManyToOne
    private Image image;

    @ManyToMany
    private Collection<Authority> authorities = Collections.emptyList();
}

