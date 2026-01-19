package com.octoperf.blazemeter.search.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import static java.util.Objects.requireNonNull;

@Value
public class SearchRequest {

  String entity;
  int skip;
  int limit;

  @JsonCreator
  public SearchRequest(@JsonProperty("entity") final String entity,
                       @JsonProperty("skip") final int skip,
                       @JsonProperty("limit") final int limit) {
    super();
    this.entity = requireNonNull(entity);
    this.skip = skip;
    this.limit = limit;
  }
}
