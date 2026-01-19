package com.octoperf.blazemeter.test.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import static java.util.Objects.requireNonNull;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestDetailResult {

  TestDetail result;

  @JsonCreator
  public TestDetailResult(@JsonProperty("result") final TestDetail result) {
    super();
    this.result = requireNonNull(result);
  }
}
