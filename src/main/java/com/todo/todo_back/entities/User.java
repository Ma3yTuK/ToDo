package com.todo.todo_back.entities;

import java.util.Collection;
import java.util.Collections;

import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.common.aliasing.qual.Unique;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "my_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends EntityWithId {

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "authority_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private Collection<Authority> authorities = Collections.emptyList();
}

