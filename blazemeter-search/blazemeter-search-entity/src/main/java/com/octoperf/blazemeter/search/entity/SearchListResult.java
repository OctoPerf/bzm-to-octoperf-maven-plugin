package com.octoperf.blazemeter.search.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

import static java.util.Objects.requireNonNull;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchListResult<T> {

  List<T> result;

  @JsonCreator
  public SearchListResult(@JsonProperty("result") final List<T> result) {
    super();
    this.result = requireNonNull(result);
  }
}
