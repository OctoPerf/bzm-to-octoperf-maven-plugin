package com.octoperf.blazemeter.migration.testdata;

import com.fasterxml.jackson.databind.JsonNode;
import com.octoperf.blazemeter.migration.label.MigrationContextLabelService;
import com.octoperf.blazemeter.migration.metadata.CopyMetadata;
import com.octoperf.blazemeter.migration.testdata.csv.DatasourceCsvReferenceService;
import com.octoperf.blazemeter.test.entity.TestDataFileRequest;
import com.octoperf.blazemeter.test.entity.TestDependencies;
import com.octoperf.entity.design.CSVVariable;
import com.octoperf.entity.design.VariableScope;
import com.octoperf.entity.design.VariableWrapper;
import com.octoperf.maven.api.BlazemeterTests;
import com.octoperf.maven.api.OctoperfProjectFiles;
import com.octoperf.maven.api.OctoperfVariables;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Slf4j
@Service
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
class TestDataMigrations implements TestDataMigrationService {
  @NonNull
  BlazemeterTests bzmT;
  @NonNull
  OctoperfProjectFiles projectFiles;
  @NonNull
  OctoperfVariables variables;
  @NonNull
  MigrationContextLabelService labels;
  @NonNull
  DatasourceCsvReferenceService csvReferences;

  @Override
  public void migrateTestData(final CopyMetadata metadata) {
    metadata.getProjectIdToBzmTestIds().entries()
      .forEach(e -> {
        final String projectId = e.getKey();
        final int bzmTestId = e.getValue();
        final int bzmWorkspaceId = metadata.getBzmTestIdToBzmWorkspaceId().get(bzmTestId);
        copyTestDataFile(projectId, bzmWorkspaceId, bzmTestId, labels.buildContextualizedLabel(projectId, metadata));
      });
  }

  void copyTestDataFile(final String projectId,
                        final int bzmWorkspaceId,
                        final int bzmTestId,
                        final String logContext) {
    findTestDataModel(bzmWorkspaceId, bzmTestId)
      .ifPresent(testDataFile -> createCsvVariable(projectId, bzmTestId, logContext, testDataFile));
  }

  Optional<ResponseBody> findTestDataModel(final int bzmWorkspaceId, final int bzmTestId) {
    return bzmT.getTest(bzmTestId)
      .getDependencies()
      .flatMap(TestDependencies::getData)
      .map(testData -> generateTestDataFile(bzmWorkspaceId, bzmTestId, testData));
  }

  ResponseBody generateTestDataFile(final int bzmWorkspaceId, final int bzmTestId, final JsonNode testData) {
    final var csvRefs = csvReferences.extractCsvReferences(testData, bzmTestId);
    final var context = of(csvRefs)
      .filter(refs -> isFalse(refs.isEmpty()))
      .map(TestDataFileRequest.Context::new);
    final var attributes = TestDataFileRequest.Attributes
      .builder()
      .model(testData)
      .context(context)
      .build();
    final var data = TestDataFileRequest.Data
      .builder()
      .type("datamodel")
      .attributes(attributes)
      .build();
    return bzmT.getTestDataFile(bzmWorkspaceId, new TestDataFileRequest(data));
  }

  void createCsvVariable(final String projectId,
                         final int bzmTestId,
                         final String logContext,
                         final ResponseBody testDataFile) {
    final String filename = "test-data-" + bzmTestId + ".csv";
    projectFiles.upload(projectId, filename, testDataFile.byteStream(), testDataFile.contentType());

    final VariableWrapper csvVariable = variables.createVariable(
      VariableWrapper.builder()
        .id("")
        .userId("")
        .name("test-data-" + bzmTestId + "-csv")
        .description("")
        .projectId(projectId)
        .variable(CSVVariable.builder()
          .allowQuotedData(true)
          .delimiter(",")
          .encoding("")
          .filename(filename)
          .ignoreFirstLine(false)
          .names(List.of())
          .offset(0)
          .isRecycleOnEOF(true)
          .scope(VariableScope.SHARED)
          .isShuffle(false)
          .isStopThreadOnEOF(false)
          .build())
        .build()
    );
    log.info("[PROJECT TEST DATA - {}] '{}'", logContext, filename);
    log.info("[PROJECT TEST DATA - {}] '{}' variable created (id={})", logContext, csvVariable.getName(), csvVariable.getId());
  }
}
