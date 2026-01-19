package com.octoperf.blazemeter.migration.label;

import com.octoperf.blazemeter.migration.metadata.CopyMetadata;

public interface MigrationContextLabelService {

  String buildContextualizedLabel(String projectId, CopyMetadata copyMetadata);
}
