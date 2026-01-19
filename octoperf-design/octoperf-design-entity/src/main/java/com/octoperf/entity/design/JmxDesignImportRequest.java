package com.octoperf.entity.design;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import static java.util.Objects.requireNonNull;

@Value
@Builder
public class JmxDesignImportRequest {
  HtmlResources resources;
  AdBlocking adBlocking;

  @JsonCreator
  JmxDesignImportRequest(@JsonProperty("resources") final HtmlResources resources,
                         @JsonProperty("adBlocking")  final AdBlocking adBlocking) {
    this.resources = requireNonNull(resources);
    this.adBlocking = requireNonNull(adBlocking);
  }
}
