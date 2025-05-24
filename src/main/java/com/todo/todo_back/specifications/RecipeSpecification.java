package com.todo.todo_back.specifications;

import com.todo.todo_back.entities.*;
import jakarta.persistence.criteria.*;
import org.hibernate.query.criteria.JpaRoot;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecipeSpecification {

    private RecipeSpecification() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<Recipe> filteredRecipes(
            String searchQuery,
            Boolean isVerified,
            Collection<Long> categoryIds,
            Collection<Long> lifeStyleIds,
            Collection<Long> ingredientIds,
            Collection<Long> userIds,
            Collection<Long> excludingUserIds,
            Collection<Long> amongRecipeIds,
            Long likedUserId
    ) {
        return (root, query, builder) -> {
            assert query != null;

            List<Predicate> predicates = new ArrayList<>();

            if (searchQuery != null) {
                predicates.add(builder.like(builder.lower(root.get(Recipe.Fields.NAME.getDatabaseFieldName())), "%" + searchQuery.toLowerCase() + "%"));
            }

            if (isVerified != null) {
                predicates.add(builder.equal(root.get(Recipe.Fields.IS_VERIFIED.getDatabaseFieldName()), isVerified));
            }

            if (likedUserId != null) {
                Join<Recipe, FavoriteInstance> recipeFavoriteInstanceJoin = root.join(Recipe.Fields.LIKES.getDatabaseFieldName());
                Join<FavoriteInstance, User> favoriteInstanceUserJoin = recipeFavoriteInstanceJoin.join(FavoriteInstance.Fields.USER.getDatabaseFieldName());

                predicates.add(builder.equal(favoriteInstanceUserJoin.get(User.Fields.ID.getDatabaseFieldName()), likedUserId));
            }

            if (!amongRecipeIds.isEmpty()) {
                predicates.add(root.get(Recipe.Fields.ID.getDatabaseFieldName()).in(amongRecipeIds));
            }

            if (!categoryIds.isEmpty()) {
                Join<Recipe, Category> recipeCategoryJoin = root.join(Recipe.Fields.CATEGORIES.getDatabaseFieldName());

                predicates.add(recipeCategoryJoin.get(Category.Fields.ID.getDatabaseFieldName()).in(categoryIds));
            }

            if (!userIds.isEmpty() || !excludingUserIds.isEmpty()) {
                Join<Recipe, User> recipeUserJoin = root.join(Recipe.Fields.USER.getDatabaseFieldName());

                if (!userIds.isEmpty()) {
                    predicates.add(recipeUserJoin.get(User.Fields.ID.getDatabaseFieldName()).in(userIds));
                } else {
                    predicates.add(recipeUserJoin.get(User.Fields.ID.getDatabaseFieldName()).in(excludingUserIds).not());
                }
            }

            if (!ingredientIds.isEmpty() || !lifeStyleIds.isEmpty()) {
                Join<Recipe, RecipeConversion> recipeRecipeConversionJoin = root.join(Recipe.Fields.INGREDIENTS.getDatabaseFieldName());
                Join<RecipeConversion, IngredientUnitConversion> recipeRecipeConversionConversionJoin = recipeRecipeConversionJoin.join(RecipeConversion.Fields.CONVERSION.getDatabaseFieldName());
                Join<IngredientUnitConversion, Ingredient> recipeRecipeConversionConversionIngredientJoin = recipeRecipeConversionConversionJoin.join(IngredientUnitConversion.Fields.INGREDIENT.getDatabaseFieldName());

                if (!ingredientIds.isEmpty()) {
                    predicates.add(recipeRecipeConversionConversionIngredientJoin.get(Ingredient.Fields.ID.getDatabaseFieldName()).in(ingredientIds));
                }

                if (!lifeStyleIds.isEmpty()) {
                    Join<Ingredient, LifeStyle> recipeRecipeConversionConversionIngredientLifeStyleJoin = recipeRecipeConversionConversionIngredientJoin.join(Ingredient.Fields.LIFE_STYLES.getDatabaseFieldName());

                    predicates.add(recipeRecipeConversionConversionIngredientLifeStyleJoin.get(LifeStyle.Fields.ID.getDatabaseFieldName()).in(lifeStyleIds));
                }
            }

            return builder.and(
                    predicates.toArray(new Predicate[0])
            );
        };
    }
}
