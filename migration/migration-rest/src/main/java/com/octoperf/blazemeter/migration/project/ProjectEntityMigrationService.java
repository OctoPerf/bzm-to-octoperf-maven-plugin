package com.octoperf.blazemeter.migration.project;

import com.octoperf.entity.design.Project;

public interface ProjectEntityMigrationService {

  Project migrateProjectEntity(String projectName,
                               String workspaceId,
                               boolean alwaysCreateNewProject);
}
