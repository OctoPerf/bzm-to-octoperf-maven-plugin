package com.octoperf.blazemeter.migration.testdata.csv;

import com.fasterxml.jackson.databind.JsonNode;
import com.octoperf.blazemeter.test.entity.TestDataFileRequest;

import java.util.List;

public interface DatasourceCsvReferenceService {

  List<TestDataFileRequest.CsvReference> extractCsvReferences(JsonNode model, int bzmTestId);
}
