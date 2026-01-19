package com.octoperf.blazemeter.migration.workspace;

import com.octoperf.maven.api.OctoperfWorkspaces;
import com.octoperf.workspace.entity.Workspace;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

import static com.google.common.base.Suppliers.memoize;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
class WorkspaceEntityMigrations implements WorkspaceEntityMigrationService {
  OctoperfWorkspaces workspaces;
  Supplier<List<Workspace>> workspacesSupplier;

  WorkspaceEntityMigrations(final OctoperfWorkspaces workspaces) {
    this.workspaces = requireNonNull(workspaces);
    this.workspacesSupplier = memoize(workspaces::getWorkspaces);
  }

  @Override
  public Workspace migrateWorkspaceEntity(final String workspaceName, final boolean alwaysCreateNewWorkspace) {
    if (alwaysCreateNewWorkspace) {
      return workspaces.createWorkspace(workspaceName);
    }
    return workspacesSupplier.get()
      .stream()
      .filter(w -> w.getName().equals(workspaceName))
      .findFirst()
      .orElseGet(() -> migrateWorkspaceEntity(workspaceName, true));
  }
}
