package com.octoperf.maven.rest;

import com.octoperf.maven.api.OctoperfWorkspaces;
import com.octoperf.tools.retrofit.CallService;
import com.octoperf.workspace.entity.Workspace;
import com.octoperf.workspace.rest.api.OctoperfWorkspacesApi;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.List;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class OctoperfRestWorkspaces implements OctoperfWorkspaces {
  @NonNull
  OctoperfWorkspacesApi api;
  @NonNull
  CallService calls;

  @Override
  public Workspace createWorkspace(final String name) {
    final DateTime now = DateTime.now();
    return calls
      .execute(api.create(
        Workspace.builder()
          .id("")
          .userId("")
          .name(name)
          .description("Imported from BlazeMeter")
          .created(now)
          .lastModified(now)
          .build()
      )).orElseThrow(() -> new RuntimeException("Unable to create workspace '" + name + "'"));
  }

  @Override
  public List<Workspace> getWorkspaces() {
    return calls
      .execute(api.workspaces()).orElseThrow(() -> new RuntimeException("Unable to list workspaces"));
  }
}
