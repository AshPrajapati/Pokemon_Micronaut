package com.example.power;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import java.util.List;

@Repository
public interface PowerRepository extends CrudRepository<Power, Integer> {
  List<Power> findAll();
}
