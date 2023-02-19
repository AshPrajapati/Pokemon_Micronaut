package com.example.power;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.List;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

@MicronautTest
public class PowerTest {

  private Power newPower;

  public PowerTest() {
    this.newPower = new Power(14, "Lightning");
  }

  @Test
  public void createNewPower(RequestSpecification spec) {
    ExtractableResponse<Response> res =
        spec.given()
            .body("{\"name\":\"Lightning\"}")
            .header("Content-Type", "application/json")
            .when()
            .post("/powers")
            .then()
            .statusCode(HttpStatus.SC_CREATED)
            .assertThat()
            .body("name", Matchers.equalTo("Lightning"))
            .body("id", Matchers.any(Integer.class))
            .extract();
    newPower =
        new Power(res.body().jsonPath().getInt("id"), res.body().jsonPath().getString("name"));
  }

  @Test
  public void getAllPowers(RequestSpecification spec) {

    ExtractableResponse<Response> res =
        spec.when().get("/powers").then().statusCode(HttpStatus.SC_OK).extract();
    Assertions.assertThat(res.body().jsonPath().getList("name"))
        .containsAnyElementsOf(List.of(newPower.getName()));
  }

  @Test
  public void getPowerById(RequestSpecification spec) {
    spec.given()
        .pathParam("id", newPower.getId())
        .when()
        .get("/powers/{id}")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .assertThat()
        .body("name", Matchers.equalTo(newPower.getName()))
        .body("id", Matchers.equalTo(newPower.getId()));
  }

  @Test
  public void updateById(RequestSpecification spec) {
    ExtractableResponse<Response> res =
        spec.given()
            .pathParam("id", newPower.getId())
            .body("{\"name\":\"UPDATEDNAME\"}")
            .header("Content-Type", "application/json")
            .when()
            .put("/powers/{id}")
            .then()
            .statusCode(HttpStatus.SC_CREATED)
            .assertThat()
            .body("name", Matchers.equalTo("UPDATEDNAME"))
            .extract();
    newPower.setName(res.body().jsonPath().getString("name"));
  }

  @Test
  public void deleteById(@NotNull RequestSpecification spec) {
    spec.given()
        .pathParam("id", newPower.getId())
        .when()
        .delete("/powers/{id}")
        .then()
        .statusCode(HttpStatus.SC_OK);
  }
}
