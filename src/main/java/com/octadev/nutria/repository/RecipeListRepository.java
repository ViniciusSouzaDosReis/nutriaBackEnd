package com.octadev.nutria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.octadev.nutria.models.RecipeList;

public interface RecipeListRepository extends JpaRepository<RecipeList, Long> {

}