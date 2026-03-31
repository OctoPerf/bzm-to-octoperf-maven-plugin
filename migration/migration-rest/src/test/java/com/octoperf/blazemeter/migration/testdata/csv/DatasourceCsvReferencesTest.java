package com.octoperf.blazemeter.migration.testdata.csv;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.octoperf.blazemeter.test.entity.TestDataFileRequest;
import com.octoperf.maven.api.BlazemeterTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.List;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.quality.Strictness.LENIENT;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = LENIENT)
class DatasourceCsvReferencesTest {

  static final ObjectMapper MAPPER = new ObjectMapper();

  @Mock
  BlazemeterTests bzmT;

  DatasourceCsvReferences service;

  @BeforeEach
  void setup() {
    service = new DatasourceCsvReferences(bzmT);
  }

  @Test
  void shouldExtractCsvReferences() throws Exception {
    when(bzmT.getTestFileLink(anyInt(), eq("credentials.csv"))).thenReturn(of("https://example.com/credentials.csv"));

    final JsonNode model = MAPPER.readTree("""
      {
        "entities": {
          "resources_credentials": {
            "datasources": [
              {"type": "csv", "name": "credentials.csv", "id": {"fileName": "credentials.csv"}, "loop": false}
            ]
          }
        }
      }
      """);

    final List<TestDataFileRequest.CsvReference> result = service.extractCsvReferences(model, 1);

    assertThat(result).hasSize(1);
    assertThat(result.get(0).getName()).isEqualTo("credentials.csv");
    assertThat(result.get(0).getLocation()).isEqualTo("https://example.com/credentials.csv");
  }

  @Test
  void shouldReturnEmptyWhenNoEntities() throws Exception {
    final JsonNode model = MAPPER.readTree("{}");

    assertThat(service.extractCsvReferences(model, 1)).isEmpty();
  }

  @Test
  void shouldReturnEmptyWhenNoDataSources() throws Exception {
    final JsonNode model = MAPPER.readTree("""
      {"entities": {"entity1": {}}}
      """);

    assertThat(service.extractCsvReferences(model, 1)).isEmpty();
  }

  @Test
  void shouldSkipNonCsvDataSources() throws Exception {
    final JsonNode model = MAPPER.readTree("""
      {
        "entities": {
          "entity1": {
            "datasources": [
              {"type": "json", "name": "data.json"}
            ]
          }
        }
      }
      """);

    assertThat(service.extractCsvReferences(model, 1)).isEmpty();
  }

  @Test
  void shouldSkipCsvWhenFileNotFound() throws Exception {
    when(bzmT.getTestFileLink(anyInt(), eq("missing.csv"))).thenReturn(empty());

    final JsonNode model = MAPPER.readTree("""
      {
        "entities": {
          "entity1": {
            "datasources": [
              {"type": "csv", "name": "missing.csv"}
            ]
          }
        }
      }
      """);

    assertThat(service.extractCsvReferences(model, 1)).isEmpty();
  }
}
