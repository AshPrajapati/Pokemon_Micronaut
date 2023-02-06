package com.example.pokemon;

import io.micronaut.http.annotation.*;

import java.util.List;

@Controller("/pokemon")
public class PokemonController {

  private final PokemonService pokemonService;

  public PokemonController(PokemonService pokemonService) {
    this.pokemonService = pokemonService;
  }

  @Get
  public List<Pokemon> get() {
    return pokemonService.get();
  }

  @Post
  public Pokemon create(@Body Pokemon pokemon) {
    return pokemonService.create(pokemon);
  }

  @Put
  public Pokemon update(@Body Pokemon pokemon) {
    return pokemonService.update(pokemon);
  }

  @Get(value = "/{id}")
  public Pokemon getPokemon(@PathVariable Integer id) {
    return pokemonService.getPokemon(id);
  }

  @Delete(value = "/{id}")
  public void deletePokemon(@PathVariable Integer id) {
    pokemonService.deletePokemon(id);
  }
}
