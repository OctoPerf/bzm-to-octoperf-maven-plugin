package com.octoperf.blazemeter.migration.testdata;

import com.octoperf.blazemeter.migration.metadata.CopyMetadata;

public interface TestDataMigrationService {

  void migrateTestData(CopyMetadata metadata);
}
