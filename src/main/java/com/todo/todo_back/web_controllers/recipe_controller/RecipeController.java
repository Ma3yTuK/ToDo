package com.todo.todo_back.web_controllers.recipe_controller;

import com.todo.todo_back.entities.*;
import com.todo.todo_back.repositories.*;
import com.todo.todo_back.specifications.RecipeSpecification;
import com.todo.todo_back.specifications.UserSpecification;
import com.todo.todo_back.utilities.Authorities;
import com.todo.todo_back.web_controllers.recipe_controller.nested_dtos.RecipeIngredientDTO;
import com.todo.todo_back.web_controllers.recipe_controller.nested_dtos.RecipeIngredientUpdateDTO;
import com.todo.todo_back.web_controllers.recipe_controller.nested_dtos.RecipeStepDTO;
import com.todo.todo_back.web_controllers.recipe_controller.nested_dtos.RecipeStepUpdateDTO;
import com.todo.todo_back.web_controllers.user_controller.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@RestController
@RequestMapping("recipes")
public class RecipeController {
    private final RecipeRepository recipeRepository;
    private final ImageRepository imageRepository;
    private final UserController userController;
    private final FavoriteRepository favoriteRepository;
    private final ReviewRepository reviewRepository;
    private final IngredientRepository ingredientRepository;
    private final ConversionRepository conversionRepository;

    @GetMapping("/sorting_variants")
    public Collection<String> getSortingVariants() {
        return List.of(Recipe.Fields.NAME.getDatabaseFieldName(), Recipe.Fields.IS_PREMIUM.getDatabaseFieldName());
    }

    @GetMapping("/all")
    public Page<RecipeShortDTO> getAllRecipes(RecipeRequestParams recipeRequestParams, Authentication authentication) {
        User currentUser = userController.getUserFromAuthentication(authentication);

        List<Recipe> liked = recipeRepository.findAll(
                RecipeSpecification.filteredRecipes(
                        recipeRequestParams.getSearchQuery(),
                        recipeRequestParams.getCategoryIds(),
                        recipeRequestParams.getLifeStyleIds(),
                        recipeRequestParams.getIngredientIds(),
                        recipeRequestParams.getUserIds(),
                        Collections.emptyList(),
                        currentUser.getId()
                ),
                recipeRequestParams.getPageable()
        ).stream().toList();

        return recipeRepository.findAll(
                RecipeSpecification.filteredRecipes(
                        recipeRequestParams.getSearchQuery(),
                        recipeRequestParams.getCategoryIds(),
                        recipeRequestParams.getLifeStyleIds(),
                        recipeRequestParams.getIngredientIds(),
                        recipeRequestParams.getUserIds(),
                        Collections.emptyList(),
                        null
                        ),
                recipeRequestParams.getPageable()
        ).map((recipe) -> liked.contains(recipe) ? shortDtoFromRecipe(recipe, true) : shortDtoFromRecipe(recipe, false));
    }

    @GetMapping("/makeFavourite/{recipeId}")
    public void makeFavourite(@PathVariable Long recipeId, Authentication authentication) {
        User currentUser = userController.getUserFromAuthentication(authentication);
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        favoriteRepository.save(new FavoriteInstance(null, currentUser, recipe));
    }

    @GetMapping("/{recipeId}")
    public RecipeDTO getRecipe(@PathVariable Long recipeId, Authentication authentication) {
        User currentUser = userController.getUserFromAuthentication(authentication);
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (currentUser.getAuthorities().stream().anyMatch(authority -> authority.getId().equals(Authorities.PREMIUM.getId())) && recipe.getIsPremium()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return dtoFromRecipe(recipe, currentUser);
    }

    @PostMapping("/add")
    public RecipeDTO saveRecipe(RecipeUpdateDTO recipeUpdateDTO, Authentication authentication) {
        User currentUser = userController.getUserFromAuthentication(authentication);

        Recipe recipe = new Recipe();
        fillRecipeFromDto(recipe, recipeUpdateDTO);

        recipe.setUser(currentUser);
        recipe.setIsPremium(false);

        return dtoFromRecipe(recipe, currentUser);
    }


    @PostMapping("/update/{recipeId}")
    public RecipeDTO updateRecipe(@PathVariable Long recipeId, RecipeUpdateDTO recipeUpdateDTO, Authentication authentication) {
        User currentUser = userController.getUserFromAuthentication(authentication);

        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (currentUser.getAuthorities().stream().noneMatch(authority -> authority.getId().equals(Authorities.MODERATE.getId())) && recipe.getUser() != currentUser) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        fillRecipeFromDto(recipe, recipeUpdateDTO);

        return dtoFromRecipe(recipe, currentUser);
    }

    private RecipeShortDTO shortDtoFromRecipe(Recipe recipe, Boolean isFavorite) {
        RecipeShortDTO recipeShortDTO = new RecipeShortDTO();

        recipeShortDTO.setId(recipe.getId());
        recipeShortDTO.setName(recipe.getName());
        recipeShortDTO.setCalories(recipe.getIngredients().stream().reduce(0L, (acc, recipeIngredient) -> {
            return acc + recipeIngredient.getIngredient().getCalories() * recipeIngredient.getAmount().longValue();
        }, Long::sum));
        recipeShortDTO.setCalories(recipe.getIngredients().stream().reduce(0L, (acc, recipeIngredient) -> {
            return acc + recipeIngredient.getIngredient().getCalories();
        }, Long::sum) / recipe.getIngredients().size());
        recipeShortDTO.setIsFavorite(isFavorite);
        recipeShortDTO.setIsPremium(recipe.getIsPremium());
        recipeShortDTO.setImage(recipe.getImage());

        return recipeShortDTO;
    }

    private RecipeDTO dtoFromRecipe(Recipe recipe, User user) {
        RecipeDTO recipeDTO = new RecipeDTO();

        RecipeShortDTO recipeShortDTO = shortDtoFromRecipe(recipe, favoriteRepository.findByRecipeAndUser(recipe, user).isPresent());
        recipeDTO.setId(recipeShortDTO.getId());
        recipeDTO.setName(recipeShortDTO.getName());
        recipeDTO.setCalories(recipeShortDTO.getCalories());
        recipeDTO.setImage(recipeShortDTO.getImage());
        recipeDTO.setIsFavorite(recipeShortDTO.getIsFavorite());
        recipeDTO.setIsPremium(recipeShortDTO.getIsPremium());

        Long ratingSum = reviewRepository.getRatingSum(recipe).orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
        Long reviewCount = reviewRepository.countTotalReviews(recipe).orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
        recipeDTO.setRating(ratingSum.floatValue() / reviewCount);

        recipeDTO.setDescription(recipe.getDescription());
        recipeDTO.setUser(new UserShortDTO(recipe.getUser()));
        recipeDTO.setIngredients(recipe.getIngredients().stream().map(RecipeIngredientDTO::new).toList());
        recipeDTO.setSteps(recipe.getSteps().stream().map(RecipeStepDTO::new).toList());

        return recipeDTO;
    }

    private void fillRecipeFromDto(Recipe recipe, RecipeUpdateDTO recipeUpdateDTO) {
        if (recipeUpdateDTO.getName() != null) recipe.setName(recipeUpdateDTO.getName());
        if (recipeUpdateDTO.getDescription() != null) recipe.setDescription(recipeUpdateDTO.getDescription());
        if (recipeUpdateDTO.getImageId() != null) recipe.setImage(imageRepository.findById(recipeUpdateDTO.getImageId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST)));

        Collection<RecipeStep> recipeSteps = recipeUpdateDTO.getSteps().stream().map(recipeStepUpdateDTO -> {
            RecipeStep recipeStep = new RecipeStep();
            fillRecipeStepFromDto(recipeStep, recipeStepUpdateDTO);
            return recipeStep;
        }).toList();
        recipe.setSteps(Stream.concat(recipeSteps.stream(), recipe.getSteps().stream()).toList().stream().distinct().toList());

        Collection<RecipeIngredient> recipeIngredients = recipeUpdateDTO.getIngredients().stream().map(recipeIngredientUpdateDTO -> {
            RecipeIngredient recipeIngredient = new RecipeIngredient();
            fillRecipeIngredientFromDto(recipeIngredient, recipeIngredientUpdateDTO);
            return recipeIngredient;
        }).toList();
        recipe.setIngredients(Stream.concat(recipeIngredients.stream(), recipe.getIngredients().stream()).toList().stream().distinct().toList());
    }

    private void fillRecipeStepFromDto(RecipeStep recipeStep, RecipeStepUpdateDTO recipeStepUpdateDTO) {
        if(recipeStepUpdateDTO.getId() != null) recipeStep.setId(recipeStepUpdateDTO.getId());
        if(recipeStepUpdateDTO.getDescription() != null) recipeStep.setDescription(recipeStepUpdateDTO.getDescription());
    }

    private void fillRecipeIngredientFromDto(RecipeIngredient recipeIngredient, RecipeIngredientUpdateDTO recipeIngredientUpdateDTO) {
        if(recipeIngredientUpdateDTO.getId() != null) recipeIngredient.setId(recipeIngredientUpdateDTO.getId());
        if(recipeIngredientUpdateDTO.getAmount() != null) recipeIngredient.setAmount(recipeIngredientUpdateDTO.getAmount());
        if(recipeIngredientUpdateDTO.getIngredientId() != null) recipeIngredient.setIngredient(ingredientRepository.findById(recipeIngredientUpdateDTO.getIngredientId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST)));
        if(recipeIngredientUpdateDTO.getConversionId() != null) recipeIngredient.setConversion(conversionRepository.findById(recipeIngredientUpdateDTO.getConversionId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST)));
    }
}
