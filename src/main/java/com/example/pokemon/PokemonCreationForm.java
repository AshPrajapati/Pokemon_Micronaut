package com.example.pokemon;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = false)
public class PokemonCreationForm {

  private final String name;
  private final Integer powerId;
  private final String imageUrl;

  @JsonCreator
  public PokemonCreationForm(
      @JsonProperty("name") String name, @JsonProperty("power_id") Integer powerId,@JsonProperty("image_url") String imageUrl) {

    this.name = name;
    this.powerId = powerId;
    this.imageUrl = imageUrl;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public String getName() {
    return name;
  }

  public Integer getPowerId() {
    return powerId;
  }
}
