package com.example.exception;

public class EntityNotFound extends PokemonException {
  public EntityNotFound(String message) {
    super(message);
  }
}
