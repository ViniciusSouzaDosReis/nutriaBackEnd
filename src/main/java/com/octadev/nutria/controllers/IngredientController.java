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

import com.octadev.nutria.models.Ingredient;
import com.octadev.nutria.repository.IngredientRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ingredient")
public class IngredientController {

  Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  IngredientRepository repository;

  @GetMapping
  public List<Ingredient> index() {
    return repository.findAll();
  }

  @PostMapping
  public ResponseEntity<Ingredient> create(@RequestBody @Valid Ingredient ingredient) {
    log.info("Cadastrando ingrediente: " + ingredient);
    repository.save(ingredient);
    return ResponseEntity.status(HttpStatus.CREATED).body(ingredient);
  }

  @GetMapping("{id}")
  public ResponseEntity<Ingredient> show(@PathVariable Long id) {
    log.info("Detalhando ingrediente: " + id);
    return ResponseEntity.ok(getIngredient(id));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Ingredient> destroy(@PathVariable Long id) {
    log.info("Apagando ingrediente: " + id);
    repository.delete(getIngredient(id));
    return ResponseEntity.noContent().build();
  }

  @PutMapping("{id}")
  public ResponseEntity<Ingredient> update(@PathVariable Long id, @RequestBody @Valid Ingredient ingredient) {
    log.info("Atualizando ingrediente: " + id);
    getIngredient(id);
    ingredient.setId(id);
    repository.save(ingredient);
    return ResponseEntity.ok(ingredient);
  }

  private Ingredient getIngredient(Long id) {
    return repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente não encontrado"));
  }
}
