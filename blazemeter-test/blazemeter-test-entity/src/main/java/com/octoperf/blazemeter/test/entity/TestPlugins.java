package com.octoperf.blazemeter.test.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

import static com.fasterxml.jackson.annotation.Nulls.AS_EMPTY;
import static java.util.Objects.requireNonNull;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestPlugins {
  @NonNull List<String> sharedFolders;

  @JsonCreator
  TestPlugins(@JsonProperty("sharedFolders") @JsonSetter(nulls = AS_EMPTY) final List<String> sharedFolders) {
    super();
    this.sharedFolders = requireNonNull(sharedFolders);
  }
}
