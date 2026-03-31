package com.octoperf.blazemeter.test.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

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
    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    Optional<Context> context;

    @JsonCreator
    public Attributes(@JsonProperty("model") final JsonNode model,
                      @JsonProperty("context") final Optional<Context> context) {
      this.model = requireNonNull(model);
      this.context = ofNullable(context).flatMap(c -> c);
    }
  }

  @Value
  @Builder
  public static class Context {
    List<CsvReference> csvs;

    @JsonCreator
    public Context(@JsonProperty("csvs") final List<CsvReference> csvs) {
      this.csvs = requireNonNull(csvs);
    }
  }

  @Value
  @Builder
  public static class CsvReference {
    String name;
    String location;

    @JsonCreator
    public CsvReference(@JsonProperty("name") final String name,
                        @JsonProperty("location") final String location) {
      this.name = requireNonNull(name);
      this.location = requireNonNull(location);
    }
  }
}
