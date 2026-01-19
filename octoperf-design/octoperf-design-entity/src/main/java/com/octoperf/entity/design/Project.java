package com.octoperf.entity.design;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.Value;
import org.joda.time.DateTime;
import org.jspecify.annotations.Nullable;

import java.util.Set;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project {
  String id;
  String userId;
  String workspaceId;
  String name;
  String description;
  ProjectType type;
  DateTime created;
  DateTime lastModified;
  Set<String> tags;

  @JsonCreator
  Project(@JsonProperty("id") final String id,
          @JsonProperty("userId") final String userId,
          @JsonProperty("workspaceId") final String workspaceId,
          @JsonProperty("name") final String name,
          @JsonProperty("description") final String description,
          @JsonProperty("type") final ProjectType type,
          @JsonProperty("created") final DateTime created,
          @JsonProperty("lastModified") final DateTime lastModified,
          @JsonProperty("tags") @JsonDeserialize(as = ImmutableSet.class) @Nullable final Set<String> tags) {
    super();
    this.id = requireNonNull(id);
    this.userId = requireNonNull(userId);
    this.workspaceId = requireNonNull(workspaceId);
    this.name = requireNonNull(name);
    this.description = requireNonNull(description);
    this.type = requireNonNull(type);
    this.created = requireNonNull(created);
    this.lastModified = requireNonNull(lastModified);
    this.tags = ofNullable(tags).orElseGet(ImmutableSet::of);
  }

}
