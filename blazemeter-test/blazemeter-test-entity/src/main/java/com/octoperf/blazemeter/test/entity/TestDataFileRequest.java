package com.octoperf.blazemeter.test.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Value;

import static java.util.Objects.requireNonNull;

@Value
public class TestDataFileRequest {
  Data data;

  @JsonCreator
  public TestDataFileRequest(@JsonProperty("data") final Data data) {
    this.data = requireNonNull(data);
  }

  @Value
  @Builder
  public static class Data {
    String type;
    Attributes attributes;

    @JsonCreator
    public Data(@JsonProperty("type") final String type,
                @JsonProperty("attributes") final Attributes attributes) {
      this.type = requireNonNull(type);
      this.attributes = requireNonNull(attributes);
    }
  }

  @Value
  @Builder
  public static class Attributes {
    JsonNode model;

    @JsonCreator
    public Attributes(@JsonProperty("model") final JsonNode model) {
      this.model = requireNonNull(model);
    }
  }
}
