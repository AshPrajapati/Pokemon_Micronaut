package com.example.pokemon;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PokemonRepository extends CrudRepository<Pokemon, Integer> {
  Optional<Pokemon> findByNameIgnoreCase(String name);

  List<Pokemon> findAll();
}
