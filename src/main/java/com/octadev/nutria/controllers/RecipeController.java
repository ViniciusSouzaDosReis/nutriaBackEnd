package com.octadev.nutria.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.octadev.nutria.models.Recipe;
import com.octadev.nutria.repository.RecipeRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

  Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  RecipeRepository repository;

  @GetMapping
  public List<Recipe> index() {
    return repository.findAll();
  }

  @PostMapping
  public ResponseEntity<Recipe> create(@RequestBody @Valid Recipe recipe) {
    log.info("Cadastrando receita: " + recipe);
    repository.save(recipe);
    return ResponseEntity.status(HttpStatus.CREATED).body(recipe);
  }

  @GetMapping("{id}")
  public ResponseEntity<Recipe> show(@PathVariable Long id) {
    log.info("Detalhando receita: " + id);
    return ResponseEntity.ok(getRecipe(id));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Recipe> destroy(@PathVariable Long id) {
    log.info("Apagando receita: " + id);
    repository.delete(getRecipe(id));
    return ResponseEntity.noContent().build();
  }

  @PutMapping("{id}")
  public ResponseEntity<Recipe> update(@PathVariable Long id, @RequestBody @Valid Recipe recipe) {
    log.info("Atualizando receita: " + id);
    getRecipe(id);
    recipe.setId(id);
    repository.save(recipe);
    return ResponseEntity.ok(recipe);
  }

  private Recipe getRecipe(Long id) {
    return repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receita não encontrada"));
  }
}
