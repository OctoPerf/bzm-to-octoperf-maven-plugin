package com.octoperf.blazemeter.migration.testdata;

import com.google.common.collect.ImmutableMultimap;
import com.octoperf.blazemeter.migration.label.MigrationContextLabelService;
import com.octoperf.blazemeter.migration.metadata.CopyMetadata;
import com.octoperf.blazemeter.migration.metadata.CopyMetadataTest;
import com.octoperf.blazemeter.test.entity.TestDataFileRequest;
import com.octoperf.blazemeter.test.entity.TestDetailTest;
import com.octoperf.entity.design.VariableWrapper;
import com.octoperf.entity.design.VariableWrapperTest;
import com.octoperf.maven.api.BlazemeterTests;
import com.octoperf.maven.api.OctoperfProjectFiles;
import com.octoperf.maven.api.OctoperfVariables;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import java.io.InputStream;
import java.util.Map;

import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.quality.Strictness.LENIENT;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = LENIENT)
class TestDataMigrationsTest {

  @Mock
  BlazemeterTests bzmT;
  @Mock
  OctoperfProjectFiles projectFiles;
  @Mock
  OctoperfVariables variables;
  @Mock
  MigrationContextLabelService labels;
  @Mock
  ResponseBody responseBody;

  TestDataMigrations migrations;

  @BeforeEach
  void before() {
    migrations = new TestDataMigrations(bzmT, projectFiles, variables, labels);
    when(labels.buildContextualizedLabel(anyString(), any(CopyMetadata.class))).thenReturn("logContext");
    when(variables.createVariable(any(VariableWrapper.class))).thenReturn(VariableWrapperTest.newInstance());
    when(bzmT.getTest(anyInt())).thenReturn(TestDetailTest.newInstance());
    final InputStream input = InputStream.nullInputStream();
    final MediaType mediaType = MediaType.parse("text");
    when(responseBody.byteStream()).thenReturn(input);
    when(responseBody.contentType()).thenReturn(mediaType);
    when(bzmT.getTestDataFile(anyInt(), any(TestDataFileRequest.class))).thenReturn(responseBody);
  }

  @Test
  void shouldMigrateTestData() {
    final TestDataMigrations migrationsSpy = spy(migrations);
    doNothing().when(migrationsSpy).copyTestDataFile(anyString(), anyInt(), anyInt(), anyString());
    final CopyMetadata metadata = CopyMetadataTest.newBuilder()
      .projectIdToBzmTestIds(ImmutableMultimap.of("pId", 1))
      .bzmTestIdToBzmWorkspaceId(Map.of(1, 2))
      .build();

    migrationsSpy.migrateTestData(metadata);

    verify(migrationsSpy).copyTestDataFile("pId", 2,1, "logContext");
  }

  @Test
  void shouldCopyTestDataFile() {
    final TestDataMigrations migrationsSpy = spy(migrations);
    doReturn(of(responseBody)).when(migrationsSpy).findTestDataModel(1, 2);
    doNothing().when(migrationsSpy).createCsvVariable(anyString(), anyInt(), anyString(), any(ResponseBody.class));

    migrationsSpy.copyTestDataFile("pId", 1, 2, "logContext");

    verify(migrationsSpy).findTestDataModel(1, 2);
    verify(migrationsSpy).createCsvVariable(anyString(), anyInt(), anyString(), any(ResponseBody.class));
  }

  @Test
  void shouldFindTestDataModel() {
    assertThat(migrations.findTestDataModel(1, 2)).isPresent();
    verify(bzmT).getTest(2);
    verify(bzmT).getTestDataFile(anyInt(), any(TestDataFileRequest.class));
  }

  @Test
  void shouldCreateCsvVariable() {
    migrations.createCsvVariable("pId", 1, "logContext", responseBody);

    verify(projectFiles).upload("pId", "test-data-1.csv", responseBody.byteStream(), responseBody.contentType());
    verify(variables).createVariable(any(VariableWrapper.class));
  }
}