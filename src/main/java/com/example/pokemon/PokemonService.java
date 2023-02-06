package com.example.pokemon;

import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class PokemonService {

  private final PokemonRepository pokemonRepository;

  public PokemonService(PokemonRepository pokemonRepository) {
    this.pokemonRepository = pokemonRepository;
  }

  public List<Pokemon> get() {
    return (List<Pokemon>) pokemonRepository.findAll();
  }

  public Pokemon create(Pokemon pokemon) {
    return pokemonRepository.save(pokemon);
  }

  public Pokemon update(Pokemon pokemon) {
    return pokemonRepository.update(pokemon);
  }

  public Pokemon getPokemon(Integer id) {
    return pokemonRepository.findById(id).orElseThrow();
  }

  public void deletePokemon(Integer id) {
    this.pokemonRepository.deleteById(id);
  }
}
