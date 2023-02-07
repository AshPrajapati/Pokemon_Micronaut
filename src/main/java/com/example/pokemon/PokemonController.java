package com.example.pokemon;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
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
  public HttpResponse<Pokemon> create(@Body PokemonCreationForm pokemonCreationForm) {
    return HttpResponse.created(pokemonService.create(pokemonCreationForm));
  }

  @Put
  public HttpResponse<Pokemon> update(@Body Pokemon pokemon) {
    return HttpResponse.created(pokemonService.update(pokemon));
  }

  @Get(value = "/{id}")
  public HttpResponse<Pokemon> getPokemon(@PathVariable Integer id) {
    return HttpResponse.ok(pokemonService.getPokemon(id));
  }

  @Delete(value = "/{id}")
  public HttpResponse<Pokemon> deletePokemon(@PathVariable Integer id) {
    pokemonService.deletePokemon(id);
    return HttpResponse.ok();
  }
}
