package com.octoperf.blazemeter.migration.label;

import com.octoperf.blazemeter.migration.metadata.CopyMetadata;
import com.octoperf.entity.design.Project;
import com.octoperf.workspace.entity.Workspace;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
class MigrationContextLabels implements MigrationContextLabelService {

  @Override
  public String buildContextualizedLabel(final String projectId, final CopyMetadata copyMetadata) {
    final Project project = ofNullable(copyMetadata.getProjects().get(projectId))
      .orElseThrow(() -> new RuntimeException(format("Unable to retrieve project from '%s' id", projectId)));
    final Workspace workspace = ofNullable(copyMetadata.getWorkspaces().get(project.getWorkspaceId()))
      .orElseThrow(() -> new RuntimeException(format("Unable to retrieve workspace from '%s' id", project.getWorkspaceId())));
    return workspace.getName() + " > " + project.getName();
  }
}
