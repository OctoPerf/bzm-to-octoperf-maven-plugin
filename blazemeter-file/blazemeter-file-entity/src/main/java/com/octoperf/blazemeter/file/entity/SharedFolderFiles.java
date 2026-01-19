package com.octoperf.blazemeter.file.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

import static java.util.Objects.requireNonNull;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class SharedFolderFiles {

  List<SharedFolderFile> files;

  @JsonCreator
  public SharedFolderFiles(@JsonProperty("files") final List<SharedFolderFile> files) {
    super();
    this.files = requireNonNull(files);
  }
}
