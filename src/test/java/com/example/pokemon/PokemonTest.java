package com.example.pokemon;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

import com.example.power.Power;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.assertj.core.api.AbstractStringAssert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@MicronautTest
public class PokemonTest {

  private AbstractStringAssert<?> equalTo;

  @BeforeEach
  public void init() {
    RestAssured.port = 5000;
  }

  @Test
  public void addPokemon() {
    PokemonForm newPokemonForm =
        new PokemonForm("Balbasaur" + Math.random(), 1, "http://imageurl.com");
    Pokemon createdPokemon =
        given()
            .contentType(ContentType.JSON)
            .body(newPokemonForm)
            .when()
            .post("/pokemon")
            .then()
            .statusCode(HttpStatus.SC_CREATED)
            .extract()
            .as(new TypeRef<Pokemon>() {});
    Assertions.assertThat(createdPokemon.getName()).isEqualTo(newPokemonForm.getName());
    Assertions.assertThat(createdPokemon.getPower().getId()).isEqualTo(newPokemonForm.getPowerId());
    Assertions.assertThat(createdPokemon.getImageUrl()).isEqualTo(newPokemonForm.getImageUrl());
    Assertions.assertThat(createdPokemon.getId()).isNotNull();
  }

  @Test
  public void getPokemon() {
    String existingPokemonName = "Balbasaur";
    ExtractableResponse<Response> res =
        when().get("/pokemon").then().statusCode(HttpStatus.SC_OK).extract();
    Assertions.assertThat(res.body().jsonPath().getList("name")).contains(existingPokemonName);
  }

  @Test
  public void getPokemonById() {
    Pokemon existingPokemon =
        new Pokemon(1, "Balbasaur", new Power(1, "Lightning"), "http://imageurl.com");
    Pokemon foundPokemon =
        given()
            .pathParam("id", existingPokemon.getId())
            .when()
            .get("/pokemon/{id}")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .as(new TypeRef<Pokemon>() {});
    Assertions.assertThat(foundPokemon.getName()).isEqualTo(existingPokemon.getName());
    Assertions.assertThat(foundPokemon.getId()).isEqualTo(existingPokemon.getId());
    Assertions.assertThat(foundPokemon.getImageUrl()).isEqualTo(existingPokemon.getImageUrl());
  }

  @Test
  public void updatePokemonById() {
    int existingPokemonId = 2;
    PokemonForm updatedPokemonForm = new PokemonForm("Pikachu", 1, "http://imageurl.com");
    Pokemon updatedPokemon =
        given()
            .pathParam("id", existingPokemonId)
            .body(updatedPokemonForm)
            .contentType(ContentType.JSON)
            .when()
            .put("/pokemon/{id}")
            .then()
            .statusCode(HttpStatus.SC_CREATED)
            .extract()
            .as(new TypeRef<Pokemon>() {});
    Assertions.assertThat(updatedPokemon.getName()).isEqualTo(updatedPokemon.getName());
    Assertions.assertThat(updatedPokemon.getId()).isEqualTo(existingPokemonId);
    Assertions.assertThat(updatedPokemon.getPower().getId())
        .isEqualTo(updatedPokemonForm.getPowerId());
  }

  @Test
  public void deletePokemonById() {
    int existingPoekemonId = 5;
    given()
        .pathParam("id", existingPoekemonId)
        .when()
        .delete("/pokemon/{id}")
        .then()
        .statusCode(HttpStatus.SC_OK);
  }
}
