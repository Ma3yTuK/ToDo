package com.todo.todo_back.web_controllers.user_controller;

import com.todo.todo_back.entities.Image;
import com.todo.todo_back.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserShortDTO {

    public UserShortDTO(User user) {
        id = user.getId();
        name = user.getName();
        email = user.getEmail();
        isVerified = user.getIsVerified();
        image = user.getImage();
    }

    private Long id;

    private String name;

    private String email;

    private Boolean isVerified;

    private Image image;
}
