package com.octoperf.blazemeter.test.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Value;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestDependencies {
  Optional<JsonNode> data;

  @JsonCreator
  TestDependencies(
    @JsonProperty("data") final Optional<JsonNode> data) {
    super();
    this.data = requireNonNull(data);
  }
}
