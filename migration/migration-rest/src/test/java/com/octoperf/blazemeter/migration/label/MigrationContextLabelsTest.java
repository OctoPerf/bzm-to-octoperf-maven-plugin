package com.octoperf.blazemeter.migration.label;

import com.octoperf.blazemeter.migration.metadata.CopyMetadata;
import com.octoperf.blazemeter.migration.metadata.CopyMetadataTest;
import com.octoperf.entity.design.ProjectTest;
import com.octoperf.workspace.entity.WorkspaceTest;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class MigrationContextLabelsTest {

  @Test
  void shouldBuildContextualizedLabel() {
    final MigrationContextLabels labels = new MigrationContextLabels();

    final CopyMetadata.CopyMetadataBuilder builder = CopyMetadataTest.newBuilder();

    final CopyMetadata missingProject = builder.build();
    final CopyMetadata missingWorkspace = builder
      .projects(Map.of("pId", ProjectTest.newInstance()))
      .workspaces(Map.of())
      .build();
    final CopyMetadata correct = builder
      .projects(Map.of("pId", ProjectTest.newInstance()))
      .workspaces(Map.of("workspaceId", WorkspaceTest.newInstance()))
      .build();

    assertThatExceptionOfType(RuntimeException.class)
      .isThrownBy(() -> labels.buildContextualizedLabel("pId", missingProject))
      .withMessage("Unable to retrieve project from 'pId' id");
    assertThatExceptionOfType(RuntimeException.class)
      .isThrownBy(() -> labels.buildContextualizedLabel("pId", missingWorkspace))
      .withMessage("Unable to retrieve workspace from 'workspaceId' id");
    assertThat(labels.buildContextualizedLabel("pId", correct)).isEqualTo("wName > name");
  }
}