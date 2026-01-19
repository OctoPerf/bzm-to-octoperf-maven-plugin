package com.octoperf.blazemeter.migration.project;

import com.octoperf.entity.design.Project;
import com.octoperf.entity.design.ProjectTest;
import com.octoperf.maven.api.OctoperfProjects;
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
class ProjectEntityMigrationsTest {
  private static final Project FIRST_PROJECT = ProjectTest.newBuilder().workspaceId("wId1").name("pName1").build();
  private static final Project SECOND_PROJECT = ProjectTest.newBuilder().workspaceId("wId1").name("pName2").build();
  private static final Project THIRD_PROJECT = ProjectTest.newBuilder().workspaceId("wId2").name("pName3").build();

  @Mock
  OctoperfProjects projects;

  ProjectEntityMigrations migrations;

  @BeforeEach
  void before() {
    migrations = new ProjectEntityMigrations(projects);
    when(projects.createProject("wId1", "pName1")).thenReturn(FIRST_PROJECT);
    when(projects.createProject("wId2", "pName3")).thenReturn(THIRD_PROJECT);
    when(projects.projects("wId1")).thenReturn(of(FIRST_PROJECT, SECOND_PROJECT));
    when(projects.projects("wId2")).thenReturn(of(THIRD_PROJECT));
  }

  @Test
  void shouldMigrateByCreatingProjectEntity() {
    assertEquals(FIRST_PROJECT, migrations.migrateProjectEntity("wId1", "pName1", true));
    verify(projects, never()).projects(anyString());
  }

  @Test
  void shouldMigrateByUsingAlreadyExistingProjectEntity() {
    assertEquals(THIRD_PROJECT, migrations.migrateProjectEntity("wId2", "pName3", false));
    verify(projects, never()).createProject(anyString(), anyString());
    verify(projects).projects("wId2");
  }
}