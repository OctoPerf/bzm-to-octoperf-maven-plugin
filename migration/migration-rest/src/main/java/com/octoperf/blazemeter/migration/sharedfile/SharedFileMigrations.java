package com.octoperf.blazemeter.migration.sharedfile;

import com.octoperf.blazemeter.file.entity.SharedFolderFile;
import com.octoperf.blazemeter.migration.label.MigrationContextLabelService;
import com.octoperf.blazemeter.migration.metadata.CopyMetadata;
import com.octoperf.maven.api.BlazemeterFiles;
import com.octoperf.maven.api.OctoperfProjectFiles;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Service
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
class SharedFileMigrations implements SharedFileMigrationService {
  @NonNull
  final MigrationContextLabelService labels;
  @NonNull
  final OctoperfProjectFiles projectFiles;
  @NonNull
  final BlazemeterFiles bzmF;

  @Override
  public void migrateSharedFiles(final CopyMetadata metadata) {
    log.info("[PROJECT FILES] 'shared files'");

    metadata.getProjectIdToBzmFolderIds()
      .entries()
      .forEach(e -> {
        final String projectId = e.getKey();
        copySharedFile(projectId, e.getValue(), labels.buildContextualizedLabel(projectId, metadata));
      });

    log.info("[PROJECT FILES] 'shared files' done");
  }

  void copySharedFile(final String projectId, final String bzmSharedFolderId, final String logContext) {
    Try.run(() -> {
      for (final SharedFolderFile f : bzmF.getSharedFiles(bzmSharedFolderId)) {
        projectFiles.upload(projectId, f.getName(), f.getLink());
        log.info("[PROJECT FILES - {}] '{}'", logContext, f.getName());
      }
    });
  }
}
