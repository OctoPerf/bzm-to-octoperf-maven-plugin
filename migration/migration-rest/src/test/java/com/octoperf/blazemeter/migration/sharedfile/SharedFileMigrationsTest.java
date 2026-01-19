package com.octoperf.blazemeter.migration.sharedfile;

import com.google.common.collect.ImmutableMultimap;
import com.octoperf.blazemeter.file.entity.SharedFolderFile;
import com.octoperf.blazemeter.file.entity.SharedFolderFileTest;
import com.octoperf.blazemeter.migration.label.MigrationContextLabelService;
import com.octoperf.blazemeter.migration.metadata.CopyMetadata;
import com.octoperf.blazemeter.migration.metadata.CopyMetadataTest;
import com.octoperf.maven.api.BlazemeterFiles;
import com.octoperf.maven.api.OctoperfProjectFiles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import java.io.IOException;

import static java.util.List.of;
import static org.mockito.Mockito.*;
import static org.mockito.quality.Strictness.LENIENT;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = LENIENT)
class SharedFileMigrationsTest {
  private static final SharedFolderFile SHARED_FILE = SharedFolderFileTest.newInstance();

  @Mock
  MigrationContextLabelService labels;
  @Mock
  OctoperfProjectFiles projectFiles;
  @Mock
  BlazemeterFiles bzmF;

  SharedFileMigrations migrations;

  @BeforeEach
  void before() {
    migrations = new SharedFileMigrations(labels, projectFiles, bzmF);
    when(bzmF.getSharedFiles("sharedFolderId")).thenReturn(of(SHARED_FILE));
    when(labels.buildContextualizedLabel(anyString(), any(CopyMetadata.class))).thenReturn("logContext");
  }

  @Test
  void shouldMigrateSharedFiles() {
    final SharedFileMigrations migrationsSpy = spy(migrations);
    doNothing().when(migrationsSpy).copySharedFile(anyString(), anyString(), anyString());
    final CopyMetadata metadata = CopyMetadataTest.newBuilder()
      .projectIdToBzmFolderIds(ImmutableMultimap.of("projectId", "sharedFolderId"))
        .build();

    migrationsSpy.migrateSharedFiles(metadata);

    verify(migrationsSpy).copySharedFile("projectId", "sharedFolderId", "logContext");
  }

  @Test
  void shouldCopySharedFile() throws IOException {
    migrations.copySharedFile("projectId", "sharedFolderId", "logContext");
    verify(bzmF).getSharedFiles("sharedFolderId");
    verify(projectFiles).upload("projectId", SHARED_FILE.getName(), SHARED_FILE.getLink());
  }
}