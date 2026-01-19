package com.octoperf.workspace.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import org.joda.time.DateTime;

import static java.util.Objects.requireNonNull;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Workspace {
  @With
  String id;
  @With
  String userId;
  @With
  String name;
  String description;
  DateTime created;
  DateTime lastModified;

  @JsonCreator
  Workspace(
    @JsonProperty("id") final String id,
    @JsonProperty("userId") final String userId,
    @JsonProperty("name") final String name,
    @JsonProperty("description") final String description,
    @JsonProperty("created") final DateTime created,
    @JsonProperty("lastModified") final DateTime lastModified) {
    super();
    this.id = requireNonNull(id);
    this.userId = requireNonNull(userId);
    this.name = requireNonNull(name);
    this.description = requireNonNull(description);
    this.created = requireNonNull(created);
    this.lastModified = requireNonNull(lastModified);
  }
}
