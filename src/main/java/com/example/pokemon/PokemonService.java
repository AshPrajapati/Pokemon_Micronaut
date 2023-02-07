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

  public Pokemon update(Pokemon pokemon) throws PokemonNotFound{
    pokemonRepository.findById(pokemon.getId()).orElseThrow(() ->  new PokemonNotFound());
    return pokemonRepository.update(pokemon);
  }

  public Pokemon getPokemon(Integer id) throws PokemonNotFound{
    return pokemonRepository.findById(id).orElseThrow(()->new PokemonNotFound());
  }

  public void deletePokemon(Integer id) throws PokemonNotFound{
    pokemonRepository.findById(id).orElseThrow(() ->  new PokemonNotFound());
    this.pokemonRepository.deleteById(id);
  }
}
