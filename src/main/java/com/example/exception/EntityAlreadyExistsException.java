package com.example.exception;

public class EntityAlreadyExistsException extends PokemonException {
  public EntityAlreadyExistsException(String message) {
    super(message);
  }
}
