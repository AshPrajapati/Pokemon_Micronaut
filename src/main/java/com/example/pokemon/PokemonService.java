package com.example.pokemon;

import com.example.exception.EntityNotFound;
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

  public Pokemon create(PokemonCreationForm pokemonCreationForm) {
    Optional<Pokemon> byName = pokemonRepository.findByNameIgnoreCase(pokemonCreationForm.getName());
    if(byName.isPresent()) {throw new RuntimeException("Pokemon already exist with this name");}
    Pokemon pokemon = new Pokemon();
    pokemon.setName(pokemonCreationForm.getName());
    pokemon.setPower(pokemonCreationForm.getPower());
    return pokemonRepository.save(pokemon);
  }

  public Pokemon update(Pokemon pokemon) {
    pokemonRepository.findById(pokemon.getId()).orElseThrow(() -> new EntityNotFound("Pokemon not found"));
    return pokemonRepository.update(pokemon);
  }

  public Pokemon getPokemon(Integer id) {
    return pokemonRepository.findById(id).orElseThrow(() -> new EntityNotFound("Pokemon not found"));
  }

  public void deletePokemon(Integer id) {
    pokemonRepository.findById(id).orElseThrow(() -> new EntityNotFound("Pokemon not found"));
    this.pokemonRepository.deleteById(id);
  }
}
