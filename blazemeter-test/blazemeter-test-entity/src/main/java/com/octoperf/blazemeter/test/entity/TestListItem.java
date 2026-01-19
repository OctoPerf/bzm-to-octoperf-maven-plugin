package com.octoperf.blazemeter.test.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import static java.util.Objects.requireNonNull;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestListItem {
  int id;
  int projectId;
  String name;
  TestConfiguration configuration;

  @JsonCreator
  TestListItem(
    @JsonProperty("id") final int id,
    @JsonProperty("projectId") final int projectId,
    @JsonProperty("name") final String name,
    @JsonProperty("configuration") final TestConfiguration configuration) {
    super();
    this.id = id;
    this.projectId = projectId;
    this.name = requireNonNull(name);
    this.configuration = requireNonNull(configuration);
  }
}
