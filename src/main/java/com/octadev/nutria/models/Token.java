package com.octadev.nutria.models;

public record Token(
    String token,
    String type,
    String prefix
) {}
