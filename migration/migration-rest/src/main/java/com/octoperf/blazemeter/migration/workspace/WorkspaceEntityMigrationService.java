package com.octoperf.blazemeter.migration.workspace;

import com.octoperf.workspace.entity.Workspace;

public interface WorkspaceEntityMigrationService {

  Workspace migrateWorkspaceEntity(String workspaceName,
                                   boolean alwaysCreateNewWorkspace);
}
