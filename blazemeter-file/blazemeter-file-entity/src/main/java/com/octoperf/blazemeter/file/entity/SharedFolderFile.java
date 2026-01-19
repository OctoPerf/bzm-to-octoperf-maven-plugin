package com.octoperf.blazemeter.file.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import static java.util.Objects.requireNonNull;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SharedFolderFile {
  String name;
  String link;

  @JsonCreator
  SharedFolderFile(@JsonProperty("name") final String name,
                   @JsonProperty("link") final String link) {
    super();
    this.name = requireNonNull(name);
    this.link = requireNonNull(link);
  }
}
