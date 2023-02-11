package com.example.power;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

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
  public HttpResponse<Power> addPower(@Body PowerForm powerForm) {
    return HttpResponse.created(powerService.create(powerForm));
  }

  @Put(value = "/{id}")
  public HttpResponse<Power> update(@PathVariable Integer id, @Body PowerForm powerForm) {
    return HttpResponse.created(powerService.update(id, powerForm));
  }

  @Delete(value = "/{id}")
  public HttpResponse<Power> deletePower(@PathVariable Integer id) {
    powerService.deletePower(id);
    return HttpResponse.ok();
  }
}
