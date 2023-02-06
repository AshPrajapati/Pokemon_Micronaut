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

    public Pokemon put(Pokemon pokemon) {
        Pokemon pk = this.store.stream().filter(p->p.getId()==pokemon.getId()).findFirst().orElseThrow();
        pk.setName(pokemon.getName());
        pk.setPower(pokemon.getPower());
        pk.setImageUrl(pokemon.getImageUrl());
        return pk;
    }

    public Pokemon getPokemon(Integer id) {
        return this.store.stream().filter(p->p.getId()==id).findFirst().orElseThrow();
    }

    public Pokemon deletePokemon(Integer id) {
        Pokemon pokemonToBeDelete = this.store.stream().filter(p->p.getId()==id).findFirst().orElseThrow();
        store.remove(pokemonToBeDelete);
        return pokemonToBeDelete;
    }
}
