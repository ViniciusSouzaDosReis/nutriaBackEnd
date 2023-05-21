package com.octadev.nutria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.octadev.nutria.models.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

}