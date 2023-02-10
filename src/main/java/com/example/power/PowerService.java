package com.example.power;

import com.example.exception.EntityNotFound;
import jakarta.inject.Singleton;
import java.util.List;

@Singleton
public class PowerService {
  private final PowerRepository powerRepository;

  public PowerService(PowerRepository powerRepository) {
    this.powerRepository = powerRepository;
  }

  public Power get(Integer id) {
    return powerRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFound("Power not exist with this id " + id));
  }

  public List<Power> get() {
    return powerRepository.findAll();
  }

  public Power create(Power power) {
    return powerRepository.save(power);
  }
}
