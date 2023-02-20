package com.example.power;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.List;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@MicronautTest
public class PowerTest {

  @BeforeEach
  public void init() {
    RestAssured.port = 5000;
  }

  @Test
  public void createNewPower() {
    String newPowerName = "Lightning" + Math.random();
    PowerForm powerForm = new PowerForm(newPowerName);
    Power createdPower =
        given()
            .contentType(ContentType.JSON)
            .body(powerForm)
            .when()
            .post("/powers")
            .then()
            .statusCode(HttpStatus.SC_CREATED)
            .extract()
            .as(new TypeRef<>() {});
    Assertions.assertThat(createdPower.getName()).isEqualTo(newPowerName);
    Assertions.assertThat(createdPower.getId()).isNotNull();
  }

  @Test
  public void getAllPowers() {
    ExtractableResponse<Response> res =
        when().get("/powers").then().statusCode(HttpStatus.SC_OK).extract();
    Assertions.assertThat(res.body().jsonPath().getList("name"))
        .containsAnyElementsOf(List.of("Lightning"));
  }

  @Test
  public void getPowerById() {
    Power existingPower = new Power(1, "Lightning");
    given()
        .pathParam("id", existingPower.getId())
        .when()
        .get("/powers/{id}")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .assertThat()
        .body("name", Matchers.equalTo(existingPower.getName()))
        .body("id", Matchers.equalTo(existingPower.getId()));
  }

  @Test
  public void updateById() {
    PowerForm powerForm = new PowerForm("Ice");
    int powerIdForUpdate = 9;
    ExtractableResponse<Response> res =
        given()
            .pathParam("id", powerIdForUpdate)
            .contentType(ContentType.JSON)
            .body(powerForm)
            .when()
            .put("/powers/{id}")
            .then()
            .statusCode(HttpStatus.SC_CREATED)
            .assertThat()
            .body("name", Matchers.equalTo("Ice"))
            .extract();
  }

  @Test
  public void deleteById(@NotNull RequestSpecification spec) {
    int powerIdForDelete = 17;
    spec.given()
        .pathParam("id", powerIdForDelete)
        .when()
        .delete("/powers/{id}")
        .then()
        .statusCode(HttpStatus.SC_OK);
  }
}
