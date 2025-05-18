package com.todo.todo_back.web_controllers.review_controller;

import com.todo.todo_back.entities.Recipe;
import com.todo.todo_back.entities.Review;
import com.todo.todo_back.entities.User;
import com.todo.todo_back.repositories.ImageRepository;
import com.todo.todo_back.repositories.RecipeRepository;
import com.todo.todo_back.repositories.ReviewRepository;
import com.todo.todo_back.repositories.UserRepository;
import com.todo.todo_back.specifications.UserSpecification;
import com.todo.todo_back.utilities.Authorities;
import com.todo.todo_back.web_controllers.user_controller.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("reviews")
public class ReviewController {
    private final ReviewRepository reviewRepository;
    private final RecipeRepository recipeRepository;
    private final UserController userController;

    @GetMapping("/{recipeId}")
    public Page<ReviewDTO> getAllReviews(@PathVariable Long recipeId, ReviewRequestParams reviewRequestParams) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Pageable pageable = reviewRequestParams.getPageable();
        return reviewRepository.findAllByRecipe(recipe, pageable).map(ReviewDTO::new);
    }

    @GetMapping("/add")
    public ReviewDTO addReview(@RequestBody ReviewUpdateDTO reviewUpdateDTO, Authentication authentication) {
        User user = userController.getUserFromAuthentication(authentication);

        Review review = new Review();
        review.setUser(user);
        review.setRecipe(recipeRepository.findById(reviewUpdateDTO.getRecipeId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST)));
        review.setRating(reviewUpdateDTO.getRating());
        review.setComment(reviewUpdateDTO.getComment());
        review.setDate(LocalDateTime.now());

        return new ReviewDTO(reviewRepository.save(review));
    }

    @GetMapping("/delete/{recipeId}")
    public void deleteReview(@PathVariable Long recipeId, Authentication authentication) {
        User user = userController.getUserFromAuthentication(authentication);
        Review review = reviewRepository.findById(recipeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (user.getAuthorities().stream().noneMatch(authority -> authority.getId().equals(Authorities.MODERATE.getId())) && !review.getUser().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        reviewRepository.delete(review);
    }
}
