package com.octoperf.blazemeter.migration.metadata;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Multimap;
import com.octoperf.entity.design.Project;
import com.octoperf.workspace.entity.Workspace;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import java.util.Map;

@Value
@Builder
public class CopyMetadata {
  @With
  @NonNull
  Map<String, Project> projects;
  @With
  @NonNull
  Map<Integer, Integer> bzmTestIdToBzmWorkspaceId;
  @With
  @NonNull
  Map<String, Workspace> workspaces;
  @With
  @NonNull
  Multimap<String, String> projectIdToBzmFolderIds;
  @With
  @NonNull
  Multimap<String, Integer> projectIdToBzmTestIds;

  public static CopyMetadata merge(final CopyMetadata first, final CopyMetadata second) {
    return CopyMetadata.builder()
      .workspaces(
        ImmutableMap.<String, Workspace>builder()
          .putAll(first.workspaces)
          .putAll(second.workspaces)
          .buildKeepingLast()
      )
      .projects(
        ImmutableMap.<String, Project>builder()
          .putAll(first.projects)
          .putAll(second.projects)
          .buildKeepingLast()
      )
      .projectIdToBzmFolderIds(
        ImmutableSetMultimap.<String, String>builder()
          .putAll(first.projectIdToBzmFolderIds)
          .putAll(second.projectIdToBzmFolderIds)
          .build()
      )
      .projectIdToBzmTestIds(
        ImmutableSetMultimap.<String, Integer>builder()
          .putAll(first.projectIdToBzmTestIds)
          .putAll(second.projectIdToBzmTestIds)
          .build()
      )
      .bzmTestIdToBzmWorkspaceId(
        ImmutableMap.<Integer, Integer>builder()
          .putAll(first.bzmTestIdToBzmWorkspaceId)
          .putAll(second.bzmTestIdToBzmWorkspaceId)
          .buildKeepingLast()
      )
      .build();
  }
}
