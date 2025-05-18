package com.todo.todo_back.repositories;

import com.todo.todo_back.entities.*;
import org.hibernate.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByRecipe(Recipe recipe, Pageable pageable);

    @Query("SELECT COALESCE(SUM(r.rating), null) FROM Review AS r WHERE r.recipe = :recipe")
    Optional<Long> getRatingSum(@Param("recipe") Recipe recipe);

    @Query("SELECT COUNT(r) FROM Review AS r WHERE r.recipe = :recipe")
    Optional<Long> countTotalReviews(@Param("recipe") Recipe recipe);
}
