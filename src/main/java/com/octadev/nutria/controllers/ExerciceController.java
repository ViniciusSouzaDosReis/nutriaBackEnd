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

import com.octadev.nutria.models.Exercice;
import com.octadev.nutria.repository.ExerciceRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/exercice")
public class ExerciceController {

  Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  ExerciceRepository repository;

  @GetMapping
  public List<Exercice> index() {
    return repository.findAll();
  }

  @PostMapping
  public ResponseEntity<Exercice> create(@RequestBody @Valid Exercice exercice) {
    log.info("cadastrando exercicio " + exercice);
    repository.save(exercice);
    return ResponseEntity.status(HttpStatus.CREATED).body(exercice);
  }

  @GetMapping("{id}")
  public ResponseEntity<Exercice> show(@PathVariable Long id) {
    log.info("detalhando exercicio " + id);
    return ResponseEntity.ok(getExercice(id));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Exercice> destroy(@PathVariable Long id) {
    log.info("apagando exercicio " + id);
    repository.delete(getExercice(id));
    return ResponseEntity.noContent().build();
  }

  @PutMapping("{id}")
  public ResponseEntity<Exercice> update(@PathVariable Long id, @RequestBody @Valid Exercice exercice) {
    log.info("atualizando exercicio " + id);
    getExercice(id);
    exercice.setId(id);
    repository.save(exercice);
    return ResponseEntity.ok(exercice);
  }

  private Exercice getExercice(Long id) {
    return repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "exercicio não encontrada"));
  }
}
