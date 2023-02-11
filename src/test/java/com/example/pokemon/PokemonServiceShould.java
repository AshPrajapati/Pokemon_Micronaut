package com.example.pokemon;

import com.example.exception.EntityAlreadyExistsException;
import com.example.exception.EntityNotFound;
import com.example.power.Power;
import com.example.power.PowerService;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PokemonServiceShould {
  private PowerService powerService;
  private PokemonRepository pokemonRepository;
  private PokemonService pokemonService;
  private Pokemon pokemon1;
  private Pokemon pokemon2;
  private Power power1;
  private Power power2;

  @BeforeEach
  void setUp() {
    power1 = new Power(1, "grass");
    power2 = new Power(2, "electric");

    pokemon1 = new Pokemon(1, "Bulbasaur", power1, "imageUrl");
    pokemon2 = new Pokemon(2, "Pikachu", power2, "imageUrl");

    pokemonRepository = Mockito.mock(PokemonRepository.class);
    powerService = Mockito.mock(PowerService.class);

    pokemonService = new PokemonService(pokemonRepository, powerService);
  }

  @Test
  void shouldReturnPokemonList() {
    Mockito.when(pokemonRepository.findAll()).thenReturn(List.of(pokemon1, pokemon2));

    List<Pokemon> pokemonList = pokemonService.get();

    Mockito.verify(pokemonRepository).findAll();
    Assertions.assertThat(pokemonList)
        .containsExactlyInAnyOrderElementsOf(List.of(pokemon2, pokemon1));
  }

  @Test
  void shouldThrowExceptionIfPokemonNotExists() {
    int pokemonId = 10;

    Mockito.when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.ofNullable(null));

    Assertions.assertThatThrownBy(() -> pokemonService.getPokemon(pokemonId))
        .isInstanceOf(EntityNotFound.class);
  }

  @Test
  void shouldReturnPokemon() {
    int pokemonId = pokemon1.getId();

    Mockito.when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.ofNullable(pokemon1));

    Pokemon foundPokemon = pokemonService.getPokemon(pokemonId);

    Mockito.verify(pokemonRepository).findById(pokemonId);
    Assertions.assertThat(foundPokemon).isEqualTo(pokemon1);
  }

  @Test
  void shouldNotAllowToCreatePokemonIfNotExists() {
    PokemonForm pokemonForm = new PokemonForm("Pikachu", 2, "imageUrl");

    Mockito.when(pokemonRepository.findByNameIgnoreCase(pokemonForm.getName()))
        .thenReturn(Optional.ofNullable(pokemon1));

    Assertions.assertThatThrownBy(() -> pokemonService.create(pokemonForm))
        .isInstanceOf(EntityAlreadyExistsException.class);
  }

  @Test
  void shouldCreatePokemon() {
    PokemonForm pokemonForm = new PokemonForm("Raichu", 2, "imageUrl");
    Pokemon expectedPokemon =
        new Pokemon(10, pokemonForm.getName(), power2, pokemonForm.getImageUrl());

    Mockito.when(pokemonRepository.findByNameIgnoreCase(pokemonForm.getName()))
        .thenReturn(Optional.ofNullable(null));
    Mockito.when(pokemonRepository.save(Mockito.any())).thenReturn(expectedPokemon);
    Mockito.when(powerService.getById(pokemonForm.getPowerId())).thenReturn(power2);

    Pokemon createdPokemon = pokemonService.create(pokemonForm);

    Mockito.verify(pokemonRepository).findByNameIgnoreCase(pokemonForm.getName());
    Mockito.verify(powerService).getById(pokemonForm.getPowerId());
    Mockito.verify(pokemonRepository).save(Mockito.any());

    Assertions.assertThat(createdPokemon).isEqualTo(expectedPokemon);
  }

  @Test
  void shouldNotAllowToUpdatePokemonIfNotExists() {
    PokemonForm pokemonForm = new PokemonForm("Pikachu123", 2, "imageUrl");
    int pokemonId = 10;

    Mockito.when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.ofNullable(null));

    Assertions.assertThatThrownBy(() -> pokemonService.update(pokemonId, pokemonForm))
        .isInstanceOf(EntityNotFound.class);
  }

  @Test
  void shouldUpdatePokemon() {
    PokemonForm pokemonForm = new PokemonForm("Pikachu123", 2, "imageUrl");
    int pokemonId = pokemon1.getId();
    int powerId = power2.getId();

    Pokemon expectedPokemon =
        new Pokemon(pokemonId, pokemonForm.getName(), power2, pokemonForm.getImageUrl());

    Mockito.when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.ofNullable(pokemon1));
    Mockito.when(pokemonRepository.update(Mockito.any())).thenReturn(expectedPokemon);
    Mockito.when(powerService.getById(powerId)).thenReturn(power2);

    Pokemon updatedPokemon = pokemonService.update(pokemonId, pokemonForm);

    Mockito.verify(pokemonRepository).findById(pokemonId);
    Mockito.verify(pokemonRepository).update(Mockito.any());
    Mockito.verify(powerService).getById(powerId);

    Assertions.assertThat(updatedPokemon).isEqualTo(expectedPokemon);
  }

  @Test
  void shouldNotDeletePokemonIfNotFound() {
    int pokemonId = 10;

    Mockito.when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.ofNullable(null));

    Assertions.assertThatThrownBy(() -> pokemonService.deletePokemon(pokemonId))
        .isInstanceOf(EntityNotFound.class);
  }

  @Test
  void shouldDeletePokemon() {
    int pokemonId = pokemon1.getId();
    Mockito.when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.ofNullable(pokemon1));

    pokemonService.deletePokemon(pokemonId);

    Mockito.verify(pokemonRepository).findById(pokemonId);
    Mockito.verify(pokemonRepository).deleteById(pokemonId);
  }
}
