package com.example.pokemon;

import com.example.exception.EntityAlreadyExistsException;
import com.example.exception.EntityNotFound;
import com.example.power.Power;
import com.example.power.PowerService;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Singleton
public class PokemonService {

  private final PokemonRepository pokemonRepository;
  private final PowerService powerService;

  public PokemonService(PokemonRepository pokemonRepository, PowerService powerService) {
    this.pokemonRepository = pokemonRepository;
    this.powerService = powerService;
  }

  public List<Pokemon> get() {
    return pokemonRepository.findAll();
  }

  public Pokemon create(PokemonForm pokemonCreationForm) {
    Optional<Pokemon> byName =
        pokemonRepository.findByNameIgnoreCase(pokemonCreationForm.getName());
    if (byName.isPresent()) {
      throw new EntityAlreadyExistsException("Pokemon already exist with this name");
    }

    Power power = powerService.get(pokemonCreationForm.getPowerId());
    Pokemon pokemon = new Pokemon();
    pokemon.setName(pokemonCreationForm.getName());
    pokemon.setPower(power);
    pokemon.setImageUrl(pokemonCreationForm.getImageUrl());
    return pokemonRepository.save(pokemon);
  }

  public Pokemon update(Integer id, PokemonForm pokemon) {
    Pokemon foundPokemon =
        pokemonRepository.findById(id).orElseThrow(() -> new EntityNotFound("Pokemon not found"));
    Power foundPower = powerService.get(pokemon.getPowerId());
    foundPokemon.setName(pokemon.getName());
    foundPokemon.setPower(foundPower);
    foundPokemon.setImageUrl(pokemon.getImageUrl());
    return pokemonRepository.update(foundPokemon);
  }

  public Pokemon getPokemon(Integer id) {
    return pokemonRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFound("Pokemon not found"));
  }

  public void deletePokemon(Integer id) {
    pokemonRepository.findById(id).orElseThrow(() -> new EntityNotFound("Pokemon not found"));
    this.pokemonRepository.deleteById(id);
  }
}
