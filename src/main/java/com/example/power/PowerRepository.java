package com.example.power;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
public interface PowerRepository extends CrudRepository<Power, Integer> {

  Optional<Power> findByNameIgnoreCase(String name);

  List<Power> findAll();
}
