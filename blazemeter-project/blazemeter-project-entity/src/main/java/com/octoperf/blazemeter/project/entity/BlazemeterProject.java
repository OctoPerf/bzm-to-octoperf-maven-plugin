package com.octoperf.blazemeter.project.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import static java.util.Objects.requireNonNull;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlazemeterProject {
  int id;
  int workspaceId;
  String name;

  @JsonCreator
  BlazemeterProject(
    @JsonProperty("id") final int id,
    @JsonProperty("workspaceId") final int workspaceId,
    @JsonProperty("name") final String name) {
    super();
    this.id = id;
    this.workspaceId = workspaceId;
    this.name = requireNonNull(name);
  }
}
