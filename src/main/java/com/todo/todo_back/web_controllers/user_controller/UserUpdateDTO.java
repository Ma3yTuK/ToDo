package com.todo.todo_back.web_controllers.user_controller;

import com.todo.todo_back.entities.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {
    private String name = null;
    private Long imageId = null;
}
