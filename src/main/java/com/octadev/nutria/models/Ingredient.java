package com.octadev.nutria.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ingredient {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;
  private String Name;
  private String FoodGroup;
  private int Calories;
  private double Carbohydrates;
  private double Protein;
  private double Fat;
  private String ServingSize;
  private String MeasurementUnit;
  private boolean IsOrganic;
  private boolean IsGlutenFree;
  private boolean IsVegetarian;
  private boolean IsVegan;
  @ManyToMany(mappedBy = "ingredients")
    private List<Recipe> recipes;
}