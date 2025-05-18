package com.todo.todo_back.web_controllers.ingredient_controller;

import com.todo.todo_back.entities.Ingredient;
import com.todo.todo_back.repositories.IngredientRepository;
import com.todo.todo_back.specifications.UserSpecification;
import com.todo.todo_back.web_controllers.user_controller.UserRequestParams;
import com.todo.todo_back.web_controllers.user_controller.UserShortDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("users")
public class IngredientController {
    private final IngredientRepository ingredientRepository;

    @GetMapping("/all")
    public Page<IngredientDTO> getAllIngredients(IngredientRequestParams ingredientRequestParams) {
        return ingredientRepository.findAll(ingredientRequestParams.getPageable()).map(IngredientDTO::new);
    }
}
