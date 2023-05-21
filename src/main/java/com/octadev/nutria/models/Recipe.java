package com.octadev.nutria.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String description;

  @ManyToMany
  @JoinTable(name = "receita_ingredient", joinColumns = @JoinColumn(name = "receita_id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
  private List<Ingredient> ingredients;

  @ManyToMany(mappedBy = "recipes")
  private List<RecipeList> recipeLists;
  public void setRecipeList(RecipeList recipeList) {
    this.recipeLists.add(recipeList);
  }
}
