package com.octoperf.entity.design;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import static java.util.Objects.requireNonNull;

/**
 * The variable Wrapper is used to encapsulate a variable to be stored on the couchbase database.
 *
 * @author jerome
 */
@Value
@Builder(toBuilder = true)
public class VariableWrapper {
  String id;
  String userId;
  String projectId;
  String name;
  String description;
  CSVVariable variable;

  @JsonCreator
  VariableWrapper(
      @JsonProperty("id") final String id,
      @JsonProperty("userId") final String userId,
      @JsonProperty("projectId") final String projectId,
      @JsonProperty("name") final String name,
      @JsonProperty("description") final String description,
      @JsonProperty("variable") final CSVVariable variable) {
    super();
    this.id = requireNonNull(id);
    this.userId = requireNonNull(userId);
    this.projectId = requireNonNull(projectId);
    this.name = requireNonNull(name);
    this.description = requireNonNull(description);
    this.variable = requireNonNull(variable);
  }
}
