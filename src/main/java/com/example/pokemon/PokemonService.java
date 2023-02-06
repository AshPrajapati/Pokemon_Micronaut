package com.example.pokemon;

import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class PokemonService {

    private final PokemonRepository pokemonRepository;

    public PokemonService(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    public List<Pokemon> get()
    {
        return pokemonRepository.get();
    }

    public Pokemon create(Pokemon pokemon) {
        return this.pokemonRepository.create(pokemon);
    }

    public Pokemon put(Pokemon pokemon) {
        return this.pokemonRepository.put(pokemon);
    }

    public Pokemon getPokemon(Integer id) {
        return this.pokemonRepository.getPokemon(id);
    }

    public Pokemon deletePokemon(Integer id) {
        return this.pokemonRepository.deletePokemon(id);
    }
}
