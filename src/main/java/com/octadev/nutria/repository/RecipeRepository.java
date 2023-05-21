package com.octadev.nutria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.octadev.nutria.models.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
