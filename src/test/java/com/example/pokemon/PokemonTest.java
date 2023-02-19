package com.example.pokemon;

import com.example.power.Power;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

@MicronautTest
public class PokemonTest {
  Pokemon existingPokemon;
  private ExtractableResponse<Response> res;

  public PokemonTest() {
    this.existingPokemon = new Pokemon(6, "Paras", new Power(3, "Bug"), "TestImageUrl");
  }

  @Test
  public void addPokemon(RequestSpecification spec) {
    spec.given()
        .body("\"{\"name\":\"Balbasaur\",\"powerId\":3,\"imageUrl\":\"Pokemon Image\"}\"")
        .header("Content-Type", "application/json")
        .when()
        .post("/pokemon")
        .then()
        .statusCode(HttpStatus.SC_CREATED)
        .assertThat()
        .body("name", Matchers.equalTo("Balbasaur"))
        .body("power.id", Matchers.equalTo(3))
        .body("image_url", Matchers.any(String.class));
  }

  @Test
  public void getPokemon(RequestSpecification spec) {
    ExtractableResponse<Response> res =
        spec.when().get("/pokemon").then().statusCode(HttpStatus.SC_OK).extract();
    Assertions.assertThat(res.body().jsonPath().getList("name"))
        .contains(existingPokemon.getName());
  }

  @Test
  public void getPokemonById(RequestSpecification spec) {
    spec.pathParam("id", existingPokemon.getId())
        .when()
        .get("/pokemon/{id}")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .assertThat()
        .body("name", Matchers.equalTo(existingPokemon.getName()))
        .body("power.id", Matchers.equalTo(existingPokemon.getPower().getId()));
  }

  @Test
  public void updatePokemonById(@NotNull RequestSpecification spec) {
    spec.pathParam("id", existingPokemon.getId())
        .body("\"{\"name\":\"Balbasaur\"}\"")
        .header("Content-Type", "application/json")
        .when()
        .put("/pokemon/{id}")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .assertThat()
        .body("name", Matchers.equalTo("Balbasaur"));
  }

  @Test
  public void deletePokemonById(@NotNull RequestSpecification spec) {
    spec.pathParam("id", existingPokemon.getId())
        .when()
        .delete("/pokemon/{id}")
        .then()
        .statusCode(HttpStatus.SC_OK);
  }
}
