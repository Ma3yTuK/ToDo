package com.todo.todo_back.web_controllers.user_controller;

import com.todo.todo_back.entities.Authority;
import com.todo.todo_back.entities.user.User;
import com.todo.todo_back.entities.user.UserShort;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserShortDTO {

    public UserShortDTO(UserShort user) {
        id = user.getId();
        name = user.getName();
        email = user.getEmail();
        isVerified = user.getIsVerified();
        imageId = user.getImage().getId();
    }

    private Long id;

    private String name;

    private String email;

    private Boolean isVerified;

    private Long imageId;
}
