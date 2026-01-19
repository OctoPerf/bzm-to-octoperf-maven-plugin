package com.octoperf.blazemeter.file.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import static java.util.Objects.requireNonNull;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class SharedFolderListResult {

  SharedFolderFiles result;

  @JsonCreator
  public SharedFolderListResult(@JsonProperty("result") final SharedFolderFiles result) {
    super();
    this.result = requireNonNull(result);
  }
}
