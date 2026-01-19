package com.octoperf.maven.rest;

import com.octoperf.blazemeter.search.entity.SearchListResult;
import com.octoperf.blazemeter.search.helper.BlazemeterSearchRestHelper;
import com.octoperf.blazemeter.test.entity.*;
import com.octoperf.blazemeter.test.rest.api.BlazemeterTestDataFilesApi;
import com.octoperf.blazemeter.test.rest.api.BlazemeterTestsApi;
import com.octoperf.maven.api.BlazemeterTests;
import com.octoperf.tools.retrofit.CallService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.List.of;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class BlazemeterRestTests implements BlazemeterTests {
  @NonNull
  BlazemeterTestsApi testApi;
  @NonNull
  BlazemeterTestDataFilesApi testDataApi;
  @NonNull
  BlazemeterSearchRestHelper searchHelper;
  @NonNull
  CallService calls;

  @Override
  public List<TestListItem> getTests(final int projectId) {
    return searchHelper.searchAll((skip, limit) ->
      calls.execute(testApi.tests(projectId, "performance", skip, limit))
        .map(SearchListResult::getResult)
        .orElse(of())
    );
  }

  @Override
  public ResponseBody getTestDataFile(final int testId, final TestDataFileRequest r) {
    return calls.execute(testDataApi.generateFile(testId, r))
      .orElseThrow(() -> new RuntimeException("Unable to generate test data file for test id='" + testId + "'"));
  }

  @Override
  public List<TestFile> getTestFiles(final int testId) {
    return calls.execute(testApi.testFiles(testId))
      .map(SearchListResult::getResult)
      .orElse(of());
  }

  @Override
  public TestDetail getTest(final int testId) {
    return calls.execute(testApi.test(testId))
      .map(TestDetailResult::getResult)
      .orElseThrow(() -> new RuntimeException("Unable to get test with id='" + testId + "'"));
  }
}
