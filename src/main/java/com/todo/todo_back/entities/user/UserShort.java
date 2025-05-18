package com.todo.todo_back.entities.user;

import com.todo.todo_back.entities.Authority;
import com.todo.todo_back.entities.EntityWithId;
import com.todo.todo_back.entities.Image;
import jakarta.persistence.*;
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
public class UserShort implements UserEntity {

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
}
