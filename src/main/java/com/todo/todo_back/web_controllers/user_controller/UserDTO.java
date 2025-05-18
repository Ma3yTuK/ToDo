package com.todo.todo_back.web_controllers.user_controller;

import com.todo.todo_back.entities.Authority;
import com.todo.todo_back.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

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
