package com.example.power;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import java.util.List;

@Controller("/powers")
public class PowerController {

  private final PowerService powerService;

  public PowerController(PowerService powerService) {
    this.powerService = powerService;
  }

  @Get
  public List<Power> getPowers() {
    return powerService.get();
  }
}
