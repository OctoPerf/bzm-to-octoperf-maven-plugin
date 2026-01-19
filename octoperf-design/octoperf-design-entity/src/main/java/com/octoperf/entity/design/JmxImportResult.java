package com.octoperf.entity.design;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.util.List;

import static java.util.Objects.requireNonNull;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class JmxImportResult {
  List<VirtualUser> virtualUsers;

  @JsonCreator
  JmxImportResult(@JsonProperty("virtualUsers") final List<VirtualUser> virtualUsers) {
    this.virtualUsers = requireNonNull(virtualUsers);
  }
}
