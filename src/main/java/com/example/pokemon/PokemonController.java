package com.example.pokemon;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
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
  public HttpResponse<Pokemon> create(@Body PokemonForm pokemonCreationForm) {
    return HttpResponse.created(pokemonService.create(pokemonCreationForm));
  }

  @Put(value = "/{id}")
  public HttpResponse<Pokemon> update(@PathVariable Integer id, @Body PokemonForm pokemon) {
    return HttpResponse.created(pokemonService.update(id, pokemon));
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
