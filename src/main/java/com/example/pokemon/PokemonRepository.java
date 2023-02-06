package com.example.pokemon;

import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class PokemonRepository {
    private final List<Pokemon> store;
    private Integer currentId;
    public PokemonRepository() {
        store = new ArrayList<>();
        currentId=1;
        create(new Pokemon(null,"Pikachu","Thunder","Url"));
        create(new Pokemon(null,"Bulbasaur","Water","Url"));
    }

    public List<Pokemon> get()
    {
        return store;
    }


    public Pokemon create(Pokemon pokemon) {
        pokemon.setId(currentId++);
        store.add(pokemon);
        return pokemon;
    }
}
