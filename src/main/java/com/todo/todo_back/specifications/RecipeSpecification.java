package com.todo.todo_back.specifications;

import com.todo.todo_back.entities.*;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
            Collection<Long> categoryIds,
            Collection<Long> lifeStyleIds,
            Collection<Long> ingredientIds,
            Collection<Long> userIds,
            Collection<Long> checkLikedRecipeIds,
            Long likedUserId
    ) {
        return (root, query, builder) -> {
            String like = Recipe.Fields.LIKES.getDatabaseFieldName();
            String category = Recipe.Fields.CATEGORIES.getDatabaseFieldName();
            String author = Recipe.Fields.USER.getDatabaseFieldName();
            String ingredient = String.join(".", Recipe.Fields.INGREDIENTS.getDatabaseFieldName(), RecipeIngredient.Fields.INGREDIENT.getDatabaseFieldName());
            String lifeStyle = String.join(".", ingredient, Ingredient.Fields.LIFE_STYLES.getDatabaseFieldName());

            String likeId = User.Fields.ID.getDatabaseFieldName();
            String categoryId = Category.Fields.ID.getDatabaseFieldName();
            String authorId = User.Fields.ID.getDatabaseFieldName();
            String ingredientId = User.Fields.ID.getDatabaseFieldName();
            String lifeStyleId = Ingredient.Fields.ID.getDatabaseFieldName();
            String rootId = Recipe.Fields.ID.getDatabaseFieldName();

            root.fetch(category, JoinType.INNER);
            root.fetch(author, JoinType.INNER);
            root.fetch(ingredient, JoinType.INNER);
            root.fetch(lifeStyle, JoinType.INNER);

            List<Predicate> predicates = new ArrayList<>();

            if (searchQuery != null) {
                predicates.add(builder.like(builder.lower(root.get(Recipe.Fields.NAME.getDatabaseFieldName())), "%" + searchQuery.toLowerCase() + "%"));
            }

            if (likedUserId != null) {
                predicates.add(builder.equal(root.get(String.join(".", like, likeId)), likedUserId));

                if (checkLikedRecipeIds != null) {
                    predicates.add(root.get(rootId).in(checkLikedRecipeIds));
                }
            }

            if (!categoryIds.isEmpty()) {
                predicates.add(root.get(String.join(".", category, categoryId)).in(categoryIds));
            }

            if (!userIds.isEmpty()) {
                predicates.add(root.get(String.join(".", author, authorId)).in(userIds));
            }

            if (!ingredientIds.isEmpty()) {
                predicates.add(root.get(String.join(".", ingredient, ingredientId)).in(ingredientIds));
            }

            if (!lifeStyleIds.isEmpty()) {
                predicates.add(root.get(String.join(".", lifeStyle, lifeStyleId)).in(lifeStyleIds));
            }

            return builder.and(
                    predicates.toArray(new Predicate[0])
            );
        };
    }
}
