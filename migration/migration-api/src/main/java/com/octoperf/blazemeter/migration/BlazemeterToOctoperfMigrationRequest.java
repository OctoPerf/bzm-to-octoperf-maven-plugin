package com.octoperf.blazemeter.migration;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

@Value
@Builder
public class BlazemeterToOctoperfMigrationRequest {
  @NonNull Optional<Integer> bzmWorkspaceId;
  @NonNull Optional<Integer> bzmProjectId;
  boolean alwaysCreateNewWorkspace;
  boolean alwaysCreateNewProject;
}
