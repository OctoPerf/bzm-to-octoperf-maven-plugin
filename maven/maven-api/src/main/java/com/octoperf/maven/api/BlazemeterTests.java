package com.octoperf.maven.api;
import com.octoperf.blazemeter.test.entity.TestDataFileRequest;
import com.octoperf.blazemeter.test.entity.TestDetail;
import com.octoperf.blazemeter.test.entity.TestFile;
import com.octoperf.blazemeter.test.entity.TestListItem;
import okhttp3.ResponseBody;

import java.util.List;
import java.util.Optional;

public interface BlazemeterTests {

  List<TestListItem> getTests(int projectId);

  ResponseBody getTestDataFile(int testId, TestDataFileRequest r);

  List<TestFile> getTestFiles(int testId);

  Optional<String> getTestFileLink(int testId, String fileName);

  TestDetail getTest(int testId);
}
