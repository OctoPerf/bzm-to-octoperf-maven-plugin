package com.octoperf.blazemeter.migration.testdata.csv;

import com.fasterxml.jackson.databind.JsonNode;
import com.octoperf.blazemeter.test.entity.TestDataFileRequest;
import com.octoperf.maven.api.BlazemeterTests;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static java.util.stream.StreamSupport.stream;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class DatasourceCsvReferences implements DatasourceCsvReferenceService {
  @NonNull
  BlazemeterTests bzmT;

  @Override
  public List<TestDataFileRequest.CsvReference> extractCsvReferences(final JsonNode model, final int bzmTestId) {
    return ofNullable(model.get("entities"))
      .stream()
      .flatMap(entities -> entities.properties().stream())
      .map(entry -> extractFromEntity(entry.getValue(), bzmTestId))
      .flatMap(List::stream)
      .toList();
  }

  List<TestDataFileRequest.CsvReference> extractFromEntity(final JsonNode entity, final int bzmTestId) {
    return ofNullable(entity.get("datasources"))
      .filter(JsonNode::isArray)
      .stream()
      .flatMap(dataSource -> stream(dataSource.spliterator(), false))
      .flatMap(ds -> toCsvReference(ds, bzmTestId).stream())
      .toList();
  }

  Optional<TestDataFileRequest.CsvReference> toCsvReference(final JsonNode dataSource, final int bzmTestId) {
    return of(dataSource)
      .filter(ds -> "csv".equals(ds.path("type").asText()))
      .flatMap(ds -> ofNullable(ds.path("name").asText(null)))
      .flatMap(name -> bzmT.getTestFileLink(bzmTestId, name)
        .map(link -> new TestDataFileRequest.CsvReference(name, link))
      );
  }
}
