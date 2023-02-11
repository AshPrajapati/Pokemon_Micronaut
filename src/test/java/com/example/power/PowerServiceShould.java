package com.example.power;

import com.example.exception.EntityAlreadyExistsException;
import com.example.exception.EntityNotFound;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PowerServiceShould {
  private PowerRepository powerRepository;
  private PowerService powerService;
  Power power1;
  Power power2;
  Power power3;

  @BeforeEach
  void setUp() {
    powerRepository = Mockito.mock(PowerRepository.class);
    power1 = new Power(1, "grass");
    power2 = new Power(2, "bug");
    power3 = new Power(3, "electric");

    powerService = new PowerService(powerRepository);
  }

  @Test
  void shouldReturnPowerList() {
    Mockito.when(powerRepository.findAll()).thenReturn(List.of(power1, power2, power3));

    List<Power> powersList = powerService.get();

    Mockito.verify(powerRepository).findAll();
    Assertions.assertThat(powersList)
        .containsExactlyInAnyOrderElementsOf(List.of(power3, power2, power1));
  }

  @Test
  void shouldThrowErrorIfPowerNotFound() {
    int powerId = 10;
    Mockito.when(powerRepository.findById(powerId)).thenReturn(Optional.ofNullable(null));

    Assertions.assertThatThrownBy(() -> powerService.getById(powerId))
        .isInstanceOf(EntityNotFound.class);
  }

  @Test
  void shouldReturnPower() {
    int powerId = power1.getId();

    Mockito.when(powerRepository.findById(powerId)).thenReturn(Optional.ofNullable(power1));

    Power foundPower = powerService.getById(powerId);

    Mockito.verify(powerRepository).findById(powerId);
    Assertions.assertThat(foundPower).isEqualTo(power1);
  }

  @Test
  void shouldNotAllowToCreatePowerIfExists() {
    PowerForm powerForm = new PowerForm("grass");
    Mockito.when(powerRepository.findByNameIgnoreCase(powerForm.getName()))
        .thenReturn(Optional.ofNullable(power1));

    Assertions.assertThatThrownBy(() -> powerService.create(powerForm))
        .isInstanceOf(EntityAlreadyExistsException.class);
  }

  @Test
  void shouldCreatePower() {
    PowerForm powerForm = new PowerForm("grass");
    Power powerToSave = new Power();
    powerToSave.setName(powerForm.getName());

    Mockito.when(powerRepository.findByNameIgnoreCase(powerForm.getName()))
        .thenReturn(Optional.ofNullable(null));
    Mockito.when(powerRepository.save(Mockito.any())).thenReturn(powerToSave);

    Power createdPower = powerService.create(powerForm);

    Mockito.verify(powerRepository).findByNameIgnoreCase(powerForm.getName());
    Mockito.verify(powerRepository).save(Mockito.any());
    Assertions.assertThat(createdPower).isEqualTo(powerToSave);
  }

  @Test
  void shouldNotAllowToUpdateIfPowerNotExists() {
    PowerForm powerForm = new PowerForm("grass");
    int powerId = 10;

    Mockito.when(powerRepository.findById(powerId)).thenReturn(Optional.ofNullable(null));

    Assertions.assertThatThrownBy(() -> powerService.update(powerId, powerForm))
        .isInstanceOf(EntityNotFound.class);
  }

  @Test
  void shouldUpdatePower() {
    PowerForm powerForm = new PowerForm("grass");
    int powerId = power2.getId();
    Power exptectedPower = power2;
    exptectedPower.setName((powerForm.getName()));

    Mockito.when(powerRepository.findById(powerId)).thenReturn(Optional.ofNullable(power2));
    Mockito.when(powerRepository.update(Mockito.any())).thenReturn(exptectedPower);

    Power updatedPower = powerService.update(powerId, powerForm);

    Mockito.verify(powerRepository).findById(powerId);
    Mockito.verify(powerRepository).update(power2);

    Assertions.assertThat(updatedPower).isEqualTo(exptectedPower);
  }

  @Test
  void shouldNotAllowToDeleteIfPowerNotExists() {
    PowerForm powerForm = new PowerForm("grass");
    int powerId = 10;

    Mockito.when(powerRepository.findById(powerId)).thenReturn(Optional.ofNullable(null));

    Assertions.assertThatThrownBy(() -> powerService.delete(powerId))
        .isInstanceOf(EntityNotFound.class);
  }

  @Test
  void shouldDeletePower() {
    int powerId = power1.getId();

    Mockito.when(powerRepository.findById(powerId)).thenReturn(Optional.ofNullable(power1));

    powerService.delete(powerId);

    Mockito.verify(powerRepository).findById(powerId);
    Mockito.verify(powerRepository).deleteById(powerId);
  }
}
