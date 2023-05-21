package com.octadev.nutria.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatRequest {
  private List<Object> messages;
  private String model;
}
