package com.todo.todo_back.web_controllers.review_controller;

import com.todo.todo_back.entities.Review;
import com.todo.todo_back.entities.User;
import com.todo.todo_back.web_controllers.user_controller.UserShortDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

    public ReviewDTO(Review review) {
        id = review.getId();
        rating = review.getRating();
        comment = review.getComment();
        moment = review.getMoment();
        user = new UserShortDTO(review.getUser());
    }

    private Long id;
    private Long rating;
    private String comment;
    private Long moment;
    private UserShortDTO user;
}
