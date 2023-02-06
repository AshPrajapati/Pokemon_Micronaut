package com.example.pokemon;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

import java.util.List;

@Controller("/pokemon")
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @Get
    public List<Pokemon> test()
    {
        return pokemonService.get();
    }

    @Post
    public Pokemon create(@Body Pokemon pokemon)
    {
        return pokemonService.create(pokemon);
    }
}
