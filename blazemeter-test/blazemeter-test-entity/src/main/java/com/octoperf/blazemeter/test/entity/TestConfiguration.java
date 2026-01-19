package com.octoperf.blazemeter.test.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.jspecify.annotations.Nullable;

import static java.util.List.of;
import static java.util.Optional.ofNullable;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestConfiguration {
  @NonNull String scriptType;
  @NonNull String filename;
  @NonNull TestPlugins plugins;

  @JsonCreator
  TestConfiguration(
    @JsonProperty("scriptType") @Nullable final String scriptType,
    @JsonProperty("filename") @Nullable final String filename,
    @JsonProperty("plugins") @Nullable final TestPlugins plugins) {
    super();
    this.scriptType = ofNullable(scriptType).orElse("");
    this.filename = ofNullable(filename).orElse("");
    this.plugins = ofNullable(plugins).orElse(new TestPlugins(of()));
  }
}
