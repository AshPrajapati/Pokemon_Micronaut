package com.example.pokemon;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = false)
public class PokemonForm {
  private final String name;
  private final Integer powerId;
  private final String imageUrl;

  @JsonCreator
  public PokemonForm(@JsonProperty("name") String name,@JsonProperty("powerId") Integer powerId, @JsonProperty("imageUrl") String imageUrl) {
    this.name = name;
    this.powerId = powerId;
    this.imageUrl = imageUrl;
  }

  public String getName() {
    return name;
  }

  public Integer getPowerId() {
    return powerId;
  }

  public String getImageUrl() {
    return imageUrl;
  }
}
