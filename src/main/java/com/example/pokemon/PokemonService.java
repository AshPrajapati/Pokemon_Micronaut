package com.example.pokemon;

import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton
public class PokemonService {

  private final PokemonRepository pokemonRepository;

  public PokemonService(PokemonRepository pokemonRepository) {
    this.pokemonRepository = pokemonRepository;
  }

  public List<Pokemon> get() {
    return pokemonRepository.findAll();
  }

  public Pokemon create(Pokemon pokemon) {
    Optional<Pokemon> byName = pokemonRepository.findByNameIgnoreCase(pokemon.getName());
    if(byName.isPresent()) {throw new RuntimeException("Pokemon already exist with this name");}
    return pokemonRepository.save(pokemon);
  }

  public Pokemon update(Pokemon pokemon) {
    pokemonRepository.findById(pokemon.getId()).orElseThrow(() -> new PokemonNotFound());
    return pokemonRepository.update(pokemon);
  }

  public Pokemon getPokemon(Integer id) {
    return pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFound());
  }

  public void deletePokemon(Integer id) {
    pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFound());
    this.pokemonRepository.deleteById(id);
  }
}
