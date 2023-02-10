package com.example.power;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
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

  @Post
  public void addPower(Power power) {
    powerService.create(power);
  }
}
