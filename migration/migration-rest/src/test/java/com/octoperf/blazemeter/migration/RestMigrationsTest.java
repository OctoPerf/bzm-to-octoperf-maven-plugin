package com.octoperf.blazemeter.migration;

import com.google.common.collect.ImmutableMultimap;
import com.octoperf.blazemeter.migration.metadata.CopyMetadata;
import com.octoperf.blazemeter.migration.metadata.CopyMetadataTest;
import com.octoperf.blazemeter.migration.project.ProjectEntityMigrationService;
import com.octoperf.blazemeter.migration.sharedfile.SharedFileMigrationService;
import com.octoperf.blazemeter.migration.testdata.TestDataMigrationService;
import com.octoperf.blazemeter.migration.workspace.WorkspaceEntityMigrationService;
import com.octoperf.blazemeter.project.entity.BlazemeterProject;
import com.octoperf.blazemeter.project.entity.BlazemeterProjectTest;
import com.octoperf.blazemeter.test.entity.TestFile;
import com.octoperf.blazemeter.test.entity.TestFileTest;
import com.octoperf.blazemeter.test.entity.TestListItem;
import com.octoperf.blazemeter.test.entity.TestListItemTest;
import com.octoperf.blazemeter.workspace.entity.BlazemeterWorkspace;
import com.octoperf.blazemeter.workspace.entity.BlazemeterWorkspaceTest;
import com.octoperf.entity.design.JmxImportResult;
import com.octoperf.entity.design.JmxImportResultTest;
import com.octoperf.maven.api.*;
import com.octoperf.workspace.entity.Workspace;
import com.octoperf.workspace.entity.WorkspaceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static com.google.common.base.Predicates.alwaysTrue;
import static com.octoperf.blazemeter.migration.RestMigrations.*;
import static java.util.List.of;
import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.quality.Strictness.LENIENT;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = LENIENT)
class RestMigrationsTest {
  private static final TestListItem TEST = TestListItemTest.newInstance();
  private static final TestFile TEST_FILE = TestFileTest.newInstance();
  private static final TestFile TEST_SCRIPT_FILE = TestFileTest.newBuilder().name("filename").build();
  private static final JmxImportResult IMPORT_RESULT = JmxImportResultTest.newInstance();
  private static final Workspace WORKSPACE = WorkspaceTest.newInstance();
  private static final BlazemeterProject BZM_PROJECT = BlazemeterProjectTest.newInstance();
  private static final BlazemeterWorkspace BZM_WORKSPACE = BlazemeterWorkspaceTest.newInstance();
  private static final BlazemeterToOctoperfMigrationRequest REQUEST = BlazemeterToOctoperfMigrationRequestTest.newBuilder()
    .bzmWorkspaceId(empty())
    .bzmProjectId(empty())
    .build();

  @Mock
  WorkspaceEntityMigrationService workspaces;
  @Mock
  ProjectEntityMigrationService projects;
  @Mock
  OctoperfProjectFiles projectFiles;
  @Mock
  OctoperfVirtualUsers vus;
  @Mock
  BlazemeterWorkspaces bzmW;
  @Mock
  BlazemeterProjects bzmP;
  @Mock
  BlazemeterTests bzmT;
  @Mock
  TestDataMigrationService testDataMigrations;
  @Mock
  SharedFileMigrationService sharedFileMigrations;

  RestMigrations migrations;

  @BeforeEach
  void before() throws IOException {
    when(bzmT.getTestFiles(anyInt())).thenReturn(of(TEST_FILE, TEST_SCRIPT_FILE));
    when(vus.importJmx(anyString(), anyString(), anyString())).thenReturn(IMPORT_RESULT);
    when(workspaces.migrateWorkspaceEntity(anyString(), anyBoolean())).thenReturn(WORKSPACE);
    when(bzmP.getProjects()).thenReturn(of(BZM_PROJECT));
    when(bzmW.getWorkspaces()).thenReturn(of(BZM_WORKSPACE));

    migrations = new RestMigrations(
      workspaces, projects, projectFiles, vus, bzmW, bzmP, bzmT, testDataMigrations, sharedFileMigrations
    );
  }

  @Test
  void shouldNotMigrate() {
    final BlazemeterToOctoperfMigrationRequest request = BlazemeterToOctoperfMigrationRequestTest.newBuilder()
      .bzmProjectId(Optional.of(1))
      .bzmWorkspaceId(empty())
      .build();

    final IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
      () -> migrations.migrate(request));
    assertTrue(e.getMessage().contains("Workspace Id is required for project migration"));
  }

  @Test
  void shouldMigrate() {
    final RestMigrations migrationsSpy = spy(migrations);
    final CopyMetadata metadata = CopyMetadataTest.newInstance();
    doReturn(metadata).when(migrationsSpy).copyBzmWorkspace(BZM_WORKSPACE, REQUEST);

    migrationsSpy.migrate(REQUEST);

    verify(bzmW).getWorkspaces();
    verify(sharedFileMigrations).migrateSharedFiles(any(CopyMetadata.class));
    verify(testDataMigrations).migrateTestData(any(CopyMetadata.class));
  }

  @Test
  void shouldBuildWorkspaceFilter() {
    assertThat(buildWorkspaceFilter(empty())).isEqualTo(alwaysTrue());
    assertThat(
      buildWorkspaceFilter(Optional.of(1)).test(BlazemeterWorkspaceTest.newInstance())
    ).isTrue();
  }

  @Test
  void shouldCopyBzmWorkspace() {
    final RestMigrations migrationsSpy = spy(migrations);
    final CopyMetadata metadata = CopyMetadataTest.newInstance();
    doReturn(metadata).when(migrationsSpy).copyBzmProject(WORKSPACE, BZM_PROJECT, REQUEST);

    assertThat(migrationsSpy.copyBzmWorkspace(BZM_WORKSPACE, REQUEST)).isNotNull();
    verify(workspaces).migrateWorkspaceEntity(BZM_WORKSPACE.getName(), REQUEST.isAlwaysCreateNewWorkspace());
    verify(bzmP).getProjects();
  }

  @Test
  void shouldBuildProjectFilter() {
    assertThat(buildProjectFilter(empty())).isEqualTo(alwaysTrue());
    assertThat(
      buildProjectFilter(Optional.of(1)).test(BlazemeterProjectTest.newInstance())
    ).isTrue();
  }

  @Test
  void shouldCopyBzmTest() throws IOException {
    assertThat(migrations.copyBzmTest("pId", 1, TEST))
      .isEqualTo(
        EMPTY_COPY
          .withProjectIdToBzmFolderIds(
            ImmutableMultimap.<String, String>builder()
              .putAll("pId", TEST.getConfiguration().getPlugins().getSharedFolders())
              .build()
          )
          .withProjectIdToBzmTestIds(ImmutableMultimap.of("pId", TEST.getId()))
          .withBzmTestIdToBzmWorkspaceId(Map.of(TEST.getId(), 1))
      );

    verify(bzmT).getTestFiles(TEST.getId());
    verify(vus).importJmx("pId", TEST.getConfiguration().getFilename(), TEST_SCRIPT_FILE.getLink());
    verify(projectFiles).upload("pId", TEST_FILE.getName(), TEST_FILE.getLink());
  }
}