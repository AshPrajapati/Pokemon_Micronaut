package com.example.power;

import com.example.exception.EntityAlreadyExistsException;
import com.example.exception.EntityNotFound;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.Optional;

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

  public Power create(PowerForm powerForm) {
    Optional<Power> byName = powerRepository.findByNameIgnoreCase(powerForm.getName());
    if (byName.isPresent()) {
      throw new EntityAlreadyExistsException("Power already exist with this name");
    }

    Power power = new Power();
    power.setName(powerForm.getName());
    return powerRepository.save(power);
  }

  public Power update(Integer id, PowerForm powerForm) {
    Power foundPower =
        powerRepository.findById(id).orElseThrow(() -> new EntityNotFound("Power doesn't exist"));
    foundPower.setName(powerForm.getName());
    return powerRepository.update(foundPower);
  }

  public void deletePower(Integer id) {
    powerRepository.findById(id).orElseThrow(() -> new EntityNotFound("Power doesn't exist"));
    this.powerRepository.deleteById(id);
  }
}
