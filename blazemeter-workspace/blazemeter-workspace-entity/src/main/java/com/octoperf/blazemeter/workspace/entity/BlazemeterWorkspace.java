package com.octoperf.blazemeter.workspace.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import static java.util.Objects.requireNonNull;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlazemeterWorkspace {
  int id;
  String name;

  @JsonCreator
  BlazemeterWorkspace(
    @JsonProperty("id") final int id,
    @JsonProperty("name") final String name) {
    super();
    this.id = id;
    this.name = requireNonNull(name);
  }
}
