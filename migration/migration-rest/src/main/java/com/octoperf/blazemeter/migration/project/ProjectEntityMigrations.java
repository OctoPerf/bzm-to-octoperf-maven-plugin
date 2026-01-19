package com.octoperf.blazemeter.migration.project;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.octoperf.entity.design.Project;
import com.octoperf.maven.api.OctoperfProjects;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
class ProjectEntityMigrations implements ProjectEntityMigrationService {
  OctoperfProjects projects;
  LoadingCache<String, List<Project>> projectsByWorkspaceIdCache;

  ProjectEntityMigrations(final OctoperfProjects projects) {
    this.projects = requireNonNull(projects);
    this.projectsByWorkspaceIdCache = Caffeine
      .newBuilder()
      .build(projects::projects);
  }

  @Override
  public Project migrateProjectEntity(final String workspaceId,
                                      final String projectName,
                                      final boolean alwaysCreateNewProject) {
    if (alwaysCreateNewProject) {
      return projects.createProject(workspaceId, projectName);
    }
    return projectsByWorkspaceIdCache.get(workspaceId)
      .stream()
      .filter(p -> p.getName().equals(projectName))
      .findFirst()
      .orElseGet(() -> migrateProjectEntity(workspaceId, projectName, true));
  }
}
