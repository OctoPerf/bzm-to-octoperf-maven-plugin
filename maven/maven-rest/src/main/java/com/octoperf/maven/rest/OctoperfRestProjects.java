package com.octoperf.maven.rest;

import com.octoperf.design.rest.api.OctoperfProjectsApi;
import com.octoperf.entity.design.Project;
import com.octoperf.maven.api.OctoperfProjects;
import com.octoperf.tools.retrofit.CallService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

import static com.octoperf.entity.design.ProjectType.DESIGN;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class OctoperfRestProjects implements OctoperfProjects {
  @NonNull
  OctoperfProjectsApi api;
  @NonNull
  CallService calls;

  @Override
  public Project createProject(final String workspaceId, final String name) {
    final DateTime now = DateTime.now();
    return calls
      .execute(api.create(
          Project.builder()
            .id("")
            .userId("")
            .workspaceId(workspaceId)
            .name(name)
            .description("")
            .type(DESIGN)
            .created(now)
            .lastModified(now)
            .tags(Set.of())
            .build()
      )).orElseThrow(() -> new RuntimeException("Unable to create project '" + name + "'"));
  }

  @Override
  public List<Project> projects(final String workspaceId) {
    return calls
      .execute(api.projects(workspaceId))
      .orElseThrow(() -> new RuntimeException("Unable to list projects for workspaceId=" + workspaceId));
  }
}
