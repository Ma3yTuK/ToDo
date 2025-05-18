package com.todo.todo_back.web_controllers.user_controller;

import com.todo.todo_back.entities.Authority;
import com.todo.todo_back.entities.Image;
import com.todo.todo_back.entities.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    public UserDTO(User user) {
        id = user.getId();
        name = user.getName();
        email = user.getEmail();
        isVerified = user.getIsVerified();
        imageId = user.getImage().getId();
        authorities = user.getAuthorities();
    }

    private Long id;

    private String name;

    private String email;

    private Boolean isVerified;

    private Long imageId;

    private Collection<Authority> authorities;
}
