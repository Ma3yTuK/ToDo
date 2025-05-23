package com.todo.todo_back.web_controllers.ingredient_controller;

import com.todo.todo_back.entities.Ingredient;
import com.todo.todo_back.repositories.IngredientRepository;
import com.todo.todo_back.specifications.UserSpecification;
import com.todo.todo_back.web_controllers.user_controller.UserRequestParams;
import com.todo.todo_back.web_controllers.user_controller.UserShortDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("ingredients")
public class IngredientController {
    private final IngredientRepository ingredientRepository;

    @GetMapping("/all")
    public PagedModel<IngredientDTO> getAllIngredients(IngredientRequestParams ingredientRequestParams) {
        return new PagedModel<>(ingredientRepository.findAll(ingredientRequestParams.getPageable()).map(IngredientDTO::new));
    }

    @GetMapping("/allWithoutPagination")
    public Collection<IngredientDTO> getAllIngredientsWithoutPagination(Optional<String> searchQuery) {
        List<IngredientDTO> result = ingredientRepository.findAll().stream().map(IngredientDTO::new).toList();
        if (searchQuery.isPresent()) {
            String lowerCaseSearchQuery = searchQuery.get().toLowerCase();
            return result.stream().filter((ingredient) -> {
                return ingredient.getName().toLowerCase().contains(lowerCaseSearchQuery);
            }).collect(Collectors.toList());
        }
        return result;
    }
}
