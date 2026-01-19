package com.octoperf.blazemeter.migration.workspace;

import com.octoperf.maven.api.OctoperfWorkspaces;
import com.octoperf.workspace.entity.Workspace;
import com.octoperf.workspace.entity.WorkspaceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.quality.Strictness.LENIENT;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = LENIENT)
class WorkspaceEntityMigrationsTest {
  private static final Workspace FIRST_WORKSPACE = WorkspaceTest.newBuilder().name("name1").build();
  private static final Workspace SECOND_WORKSPACE = WorkspaceTest.newBuilder().name("name2").build();

  @Mock
  OctoperfWorkspaces workspaces;

  WorkspaceEntityMigrations migrations;

  @BeforeEach
  void before() {
    migrations = new WorkspaceEntityMigrations(workspaces);
    when(workspaces.createWorkspace("name2")).thenReturn(SECOND_WORKSPACE);
    when(workspaces.getWorkspaces()).thenReturn(of(FIRST_WORKSPACE, SECOND_WORKSPACE));
  }

  @Test
  void shouldMigrateByCreatingWorkspaceEntity() {
    assertEquals(SECOND_WORKSPACE, migrations.migrateWorkspaceEntity("name2", true));
    verify(workspaces, never()).getWorkspaces();
  }

  @Test
  void shouldMigrateByUsingAlreadyExistingWorkspaceEntity() {
    assertEquals(SECOND_WORKSPACE, migrations.migrateWorkspaceEntity("name2", false));
    verify(workspaces, never()).createWorkspace(anyString());
    verify(workspaces).getWorkspaces();
  }
}