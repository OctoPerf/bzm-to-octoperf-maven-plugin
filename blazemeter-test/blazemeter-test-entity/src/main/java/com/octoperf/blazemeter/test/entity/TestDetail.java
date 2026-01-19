package com.octoperf.blazemeter.test.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestDetail {
  int id;
  String name;
  @JsonDeserialize(using = TestDependenciesDeserializer.class)
  Optional<TestDependencies> dependencies;

  @JsonCreator
  TestDetail(
    @JsonProperty("id") final int id,
    @JsonProperty("name") final String name,
    @JsonProperty("dependencies") final Optional<TestDependencies> testDependencies) {
    super();
    this.id = id;
    this.name = requireNonNull(name);
    this.dependencies = requireNonNull(testDependencies);
  }
}
