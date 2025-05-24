package com.todo.todo_back.web_controllers.recipe_controller;

import com.todo.todo_back.entities.*;
import com.todo.todo_back.repositories.*;
import com.todo.todo_back.specifications.RecipeSpecification;
import com.todo.todo_back.specifications.UserSpecification;
import com.todo.todo_back.utilities.Authorities;
import com.todo.todo_back.web_controllers.recipe_controller.nested_dtos.*;
import com.todo.todo_back.web_controllers.user_controller.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jruby.ir.instructions.BIntInstr;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;
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
    private final CategoryRepository categoryRepository;
    private final LifeStyleRepository lifeStyleRepository;
    private final UserRepository userRepository;
    private final RecipeConversionRepository recipeConversionRepository;
    private final RecipeStepRepository recipeStepRepository;
    private final ConversionRepository conversionRepository;

    private static final Long AMOUNT_OF_GRAM_FOR_CALORIES = 100L;

    @GetMapping("/sorting_variants")
    public Collection<String> getSortingVariants() {
        return List.of(Recipe.Fields.NAME.getDatabaseFieldName(), Recipe.Fields.IS_PREMIUM.getDatabaseFieldName());
    }

    @GetMapping("/conversion/{ingredientId}")
    public Collection<ConversionDTO> getConversions(@PathVariable Long ingredientId) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            return conversionRepository.findAllByIngredient(
                ingredient,
                Sort.by(String.join(".",
                        IngredientUnitConversion.Fields.INGREDIENT.getDatabaseFieldName(),
                        Ingredient.Fields.NAME.getDatabaseFieldName()
                )
        )).stream().map(ConversionDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/categories")
    public Collection<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("/lifestyles")
    public Collection<LifeStyle> getLifeStyles() {
        return lifeStyleRepository.findAll();
    }

    @GetMapping("/unVerified")
    public PagedModel<RecipeShortDTO> getUnVerifiedRecipes(JustPaginationRequestParams justPaginationRequestParams, Authentication authentication) {
        User currentUser = userController.getUserFromAuthentication(authentication);

        if (!currentUser.getAuthorities().stream().map(Authority::getId).toList().contains(Authorities.MODERATE.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return getAllRecipesByParams(
                null,
                false,
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                List.of(currentUser.getId()),
                null,
                Optional.of(currentUser),
                justPaginationRequestParams.getPageable()
        );
    }

    @GetMapping("/mine")
    public PagedModel<RecipeShortDTO> getMyRecipes(JustPaginationRequestParams justPaginationRequestParams, Authentication authentication) {
        User currentUser = userController.getUserFromAuthentication(authentication);

        return getAllRecipesByParams(
                null,
                null,
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                List.of(currentUser.getId()),
                Collections.emptyList(),
                null,
                Optional.of(currentUser),
                justPaginationRequestParams.getPageable()
        );
    }

    @GetMapping("/favorite")
    public PagedModel<RecipeShortDTO> getFavoriteRecipes(FavoriteRequestParams favoriteRequestParams, Authentication authentication) {
        User currentUser = userController.getUserFromAuthentication(authentication);

        return getAllRecipesByParams(
                null,
                null,
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                currentUser.getId(),
                Optional.of(currentUser),
                favoriteRequestParams.getPageable()
        );
    }

    @GetMapping("/all")
    public PagedModel<RecipeShortDTO> getAllRecipes(RecipeRequestParams recipeRequestParams, Authentication authentication) {

        Optional<User> currentUser = Optional.empty();
        if (authentication != null)
            currentUser = userRepository.findByEmail(authentication.getName());
        return getAllRecipesByParams(
                recipeRequestParams.getSearchQuery(),
                true,
                recipeRequestParams.getCategoryIds(),
                recipeRequestParams.getLifeStyleIds(),
                recipeRequestParams.getIngredientIds(),
                recipeRequestParams.getUserIds(),
                Collections.emptyList(),
                null,
                currentUser,
                recipeRequestParams.getPageable()
        );
    }

    @PostMapping("/makeFavorite/{recipeId}")
    public Boolean makeFavorite(@PathVariable Long recipeId, Authentication authentication) {
        User currentUser = userController.getUserFromAuthentication(authentication);
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Optional<FavoriteInstance> foundFavorite = favoriteRepository.findByRecipeAndUser(recipe, currentUser);

        if (foundFavorite.isPresent()) {
            favoriteRepository.delete(foundFavorite.get());
            return false;
        } else {
            favoriteRepository.save(new FavoriteInstance(null, currentUser, recipe, new Date().getTime()));
            return true;
        }
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

    @PostMapping("/saveStep")
    public RecipeStepDTO saveStep(@RequestBody RecipeStepUpdateDTO recipeUpdateDTO) {
        RecipeStep recipeStep = new RecipeStep();
        fillRecipeStepFromDto(recipeStep, recipeUpdateDTO);

        try {
            return new RecipeStepDTO(recipeStepRepository.save(recipeStep));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/updateStep/{stepId}")
    public RecipeStepDTO updateStep(@PathVariable Long stepId, @RequestBody RecipeStepUpdateDTO recipeUpdateDTO, Authentication authentication) {
        RecipeStep recipeStep = recipeStepRepository.findById(stepId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        fillRecipeStepFromDto(recipeStep, recipeUpdateDTO);

        try {
            return new RecipeStepDTO(recipeStepRepository.save(recipeStep));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/deleteStep/{stepId}")
    public void deleteStep(@PathVariable Long stepId) {
        RecipeStep recipeStep = recipeStepRepository.findById(stepId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        recipeStepRepository.delete(recipeStep);
    }

    @PostMapping("/saveIngredient")
    public RecipeConversionDTO saveIngredient(@RequestBody RecipeConversionUpdateDTO recipeConversionUpdateDTO) {
        RecipeConversion recipeConversion = new RecipeConversion();
        fillRecipeIngredientFromDto(recipeConversion, recipeConversionUpdateDTO);

        try {
            return new RecipeConversionDTO(recipeConversionRepository.save(recipeConversion));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/updateIngredient/{ingredientId}")
    public RecipeConversionDTO updateIngredient(@PathVariable Long ingredientId, @RequestBody RecipeConversionUpdateDTO recipeConversionUpdateDTO) {
        RecipeConversion recipeConversion = recipeConversionRepository.findById(ingredientId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        fillRecipeIngredientFromDto(recipeConversion, recipeConversionUpdateDTO);

        try {
            return new RecipeConversionDTO(recipeConversionRepository.save(recipeConversion));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/deleteIngredient/{ingredientId}")
    public void deleteIngredient(@PathVariable Long ingredientId) {
        RecipeConversion recipeIngredient = recipeConversionRepository.findById(ingredientId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        recipeConversionRepository.delete(recipeIngredient);
    }

    @Transactional
    @PostMapping("/add")
    public RecipeDTO saveRecipe(@RequestBody RecipeUpdateDTO recipeUpdateDTO, Authentication authentication) {
        User currentUser = userController.getUserFromAuthentication(authentication);

        Recipe recipe = new Recipe();
        recipe = fillRecipeFromDto(recipe, recipeUpdateDTO);

        recipe.setUser(currentUser);
        recipe.setIsPremium(false);
        recipe.setIsVerified(false);

        try {
            return dtoFromRecipe(recipeRepository.save(recipe), currentUser);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    @PostMapping("/update/{recipeId}")
    public RecipeDTO updateRecipe(@PathVariable Long recipeId, @RequestBody RecipeUpdateDTO recipeUpdateDTO, Authentication authentication) {
        User currentUser = userController.getUserFromAuthentication(authentication);
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        checkEditingAuthority(recipe, currentUser);

        recipe = fillRecipeFromDto(recipe, recipeUpdateDTO);
        recipe.setIsVerified(!recipe.getUser().equals(currentUser));

        recipeConversionRepository.deleteAllByRecipeId(recipe.getId());
        recipeStepRepository.deleteAllByRecipeId(recipe.getId());

        try {
            return dtoFromRecipe(recipeRepository.save(recipe), currentUser);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/delete/{recipeId}")
    public void deleteRecipe(@PathVariable Long recipeId, Authentication authentication) {
        User currentUser = userController.getUserFromAuthentication(authentication);
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        checkEditingAuthority(recipe, currentUser);

        recipeRepository.delete(recipe);
    }

    private void checkEditingAuthority(Recipe recipe, User user) {
        if (user.getAuthorities().stream().noneMatch(authority -> authority.getId().equals(Authorities.MODERATE.getId())) && recipe.getUser() != user) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    private RecipeShortDTO shortDtoFromRecipe(Recipe recipe, Boolean isFavorite) {
        RecipeShortDTO recipeShortDTO = new RecipeShortDTO();

        recipeShortDTO.setId(recipe.getId());
        recipeShortDTO.setName(recipe.getName());
        recipeShortDTO.setWeight(recipe.getWeight());
        recipeShortDTO.setCalories(recipe.getIngredients().stream().reduce(0L, (acc, recipeConversion) -> {
            return (long) (acc + recipeConversion.getConversion().getIngredient().getCalories()
                                * recipeConversion.getConversion().getCoefficient() * recipeConversion.getAmount() / AMOUNT_OF_GRAM_FOR_CALORIES);
        }, Long::sum));

        Long ratingSum = reviewRepository.getRatingSum(recipe).orElse(0L);
        Long reviewCount = reviewRepository.countTotalReviews(recipe).orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
        recipeShortDTO.setRating(reviewCount != 0 ? ratingSum.floatValue() / reviewCount : 0);

        recipeShortDTO.setIsFavorite(isFavorite);
        recipeShortDTO.setIsPremium(recipe.getIsPremium());
        recipeShortDTO.setImage(recipe.getImage());
        recipeShortDTO.setIsVerified(recipe.getIsVerified());

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
        recipeDTO.setRating(recipeShortDTO.getRating());
        recipeDTO.setIsVerified(recipeShortDTO.getIsVerified());

        recipeDTO.setWeight(recipe.getWeight());
        recipeDTO.setCategories(recipe.getCategories());
        recipeDTO.setDescription(recipe.getDescription());
        recipeDTO.setUser(new UserShortDTO(recipe.getUser()));
        recipeDTO.setIngredients(recipe.getIngredients().stream().map(RecipeConversionDTO::new).toList());
        recipeDTO.setSteps(recipe.getSteps().stream().map(RecipeStepDTO::new).sorted((e1, e2) -> (int)(e1.getIndex() - e2.getIndex())).toList());

        return recipeDTO;
    }

    private Recipe fillRecipeFromDto(Recipe recipe, RecipeUpdateDTO recipeUpdateDTO) {
        if (recipeUpdateDTO.getName() != null) recipe.setName(recipeUpdateDTO.getName());
        if (recipeUpdateDTO.getDescription() != null) recipe.setDescription(recipeUpdateDTO.getDescription());
        if (recipeUpdateDTO.getImageId() != null) recipe.setImage(imageRepository.findById(recipeUpdateDTO.getImageId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST)));
        if (recipeUpdateDTO.getWeight() != null) recipe.setWeight(recipeUpdateDTO.getWeight());

        Recipe newRecipe = recipeRepository.save(recipe);

        newRecipe.setIngredients(recipeConversionRepository.findAllById(recipeUpdateDTO.getIngredients()).stream().peek((ingredient) -> {
            ingredient.setRecipe(newRecipe);
        }).collect(Collectors.toCollection(ArrayList::new)));
        newRecipe.setSteps(recipeStepRepository.findAllById(recipeUpdateDTO.getSteps()).stream().peek((step) -> {
            step.setRecipe(newRecipe);
        }).collect(Collectors.toCollection(ArrayList::new)));

        return newRecipe;
    }

    private void fillRecipeStepFromDto(RecipeStep recipeStep, RecipeStepUpdateDTO recipeStepUpdateDTO) {
        if(recipeStepUpdateDTO.getDescription() != null) recipeStep.setDescription(recipeStepUpdateDTO.getDescription());
        if(recipeStepUpdateDTO.getImageId() != null) recipeStep.setImage(imageRepository.findById(recipeStepUpdateDTO.getImageId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST)));
        if(recipeStepUpdateDTO.getIndex() != null) recipeStep.setIndex(recipeStepUpdateDTO.getIndex());
    }

    private void fillRecipeIngredientFromDto(RecipeConversion recipeConversion, RecipeConversionUpdateDTO recipeConversionUpdateDTO) {
        if(recipeConversionUpdateDTO.getAmount() != null) recipeConversion.setAmount(recipeConversionUpdateDTO.getAmount());
        if(recipeConversionUpdateDTO.getConversionId() != null) recipeConversion.setConversion(conversionRepository.findById(recipeConversionUpdateDTO.getConversionId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST)));
    }

    private PagedModel<RecipeShortDTO> getAllRecipesByParams(
            String searchQuery,
            Boolean isVerified,
            Collection<Long> categoryIds,
            Collection<Long> lifeStyleIds,
            Collection<Long> ingredientIds,
            Collection<Long> userIds,
            Collection<Long> excludingUserIds,
            Long likedUserId,
            Optional<User> currentUser,
            Pageable pageable
    ) {
        Page<Recipe> result = recipeRepository.findAll(
                RecipeSpecification.filteredRecipes(
                        searchQuery,
                        isVerified,
                        categoryIds,
                        lifeStyleIds,
                        ingredientIds,
                        userIds,
                        excludingUserIds,
                        Collections.emptyList(),
                        likedUserId
                ),
                pageable
        );

        List<Recipe> liked = recipeRepository.findAll(
                RecipeSpecification.filteredRecipes(
                        searchQuery,
                        isVerified,
                        categoryIds,
                        lifeStyleIds,
                        ingredientIds,
                        userIds,
                        excludingUserIds,
                        result.stream().map(Recipe::getId).toList(),
                        currentUser.map(User::getId).orElse(null)
                ),
                pageable
        ).stream().toList();

        return new PagedModel<>(result.map((recipe) -> liked.contains(recipe) ? shortDtoFromRecipe(recipe, true) : shortDtoFromRecipe(recipe, false)));
    }
}
