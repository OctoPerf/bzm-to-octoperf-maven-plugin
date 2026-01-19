package com.octoperf.blazemeter.migration.metadata;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.testing.NullPointerTester;
import com.octoperf.blazemeter.migration.metadata.CopyMetadata.CopyMetadataBuilder;
import com.octoperf.entity.design.Project;
import com.octoperf.entity.design.ProjectTest;
import com.octoperf.workspace.entity.Workspace;
import com.octoperf.workspace.entity.WorkspaceTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CopyMetadataTest {

  @Test
  void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(CopyMetadata.class).verify();
  }

  @Test
  void shouldPassNullPointerTester() {
    new NullPointerTester().testConstructors(CopyMetadata.class, PACKAGE);
  }

  @Test
  void shouldCreate() {
    assertNotNull(newInstance());
  }

  @Test
  void shouldHaveNonStandardToString() {
    assertFalse(newInstance().toString().contains("@"));
  }

  @Test
  void shouldMerge() {
    final Workspace firstWorkspace = WorkspaceTest.newBuilder().id("wId1").build();
    final Workspace secondWorkspace = WorkspaceTest.newBuilder().id("wId2").build();
    final Project firstProject = ProjectTest.newBuilder().id("pId1").build();
    final Project secondProject = ProjectTest.newBuilder().id("pId2").build();

    final CopyMetadata first = CopyMetadata
      .builder()
      .workspaces(Map.of("wId1", firstWorkspace, "wId2", secondWorkspace))
      .projects(Map.of("pId1", firstProject))
      .projectIdToBzmFolderIds(ImmutableMultimap.of("pId1", "bzmFolderId1"))
      .projectIdToBzmTestIds(ImmutableMultimap.of("pId1", 1))
      .bzmTestIdToBzmWorkspaceId(Map.of(10, 20))
      .build();
    final CopyMetadata second = CopyMetadata
      .builder()
      .workspaces(Map.of("wId1", firstWorkspace))
      .projects(Map.of("pId2", secondProject))
      .projectIdToBzmFolderIds(ImmutableMultimap.of("pId2", "bzmFolderId2"))
      .projectIdToBzmTestIds(ImmutableMultimap.of("pId1", 1))
      .bzmTestIdToBzmWorkspaceId(Map.of(10, 20, 11, 21))
      .build();

    final CopyMetadata merged = CopyMetadata.merge(first, second);

    final CopyMetadata expectedMerged = CopyMetadata
      .builder()
      .workspaces(Map.of("wId1", firstWorkspace,"wId2", secondWorkspace))
      .projects(Map.of("pId2", secondProject, "pId1", firstProject))
      .projectIdToBzmFolderIds(ImmutableSetMultimap.of("pId1", "bzmFolderId1", "pId2", "bzmFolderId2"))
      .projectIdToBzmTestIds(ImmutableSetMultimap.of("pId1", 1))
      .bzmTestIdToBzmWorkspaceId(Map.of(10, 20, 11, 21))
      .build();

    assertThat(merged).isEqualTo(expectedMerged);
  }

  public static CopyMetadata newInstance() {
    return newBuilder().build();
  }

  public static CopyMetadataBuilder newBuilder() {
    return CopyMetadata
      .builder()
      .workspaces(Map.of("workspaceId", WorkspaceTest.newInstance()))
      .projects(Map.of("projectId", ProjectTest.newInstance()))
      .projectIdToBzmFolderIds(ImmutableMultimap.of("projectId", "bzmFolderId"))
      .projectIdToBzmTestIds(ImmutableMultimap.of("projectId", 1))
      .bzmTestIdToBzmWorkspaceId(Map.of(1, 2));
  }
}