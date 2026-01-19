package com.octoperf.blazemeter.migration.sharedfile;

import com.octoperf.blazemeter.migration.metadata.CopyMetadata;

public interface SharedFileMigrationService {

  void migrateSharedFiles(CopyMetadata metadata);
}
