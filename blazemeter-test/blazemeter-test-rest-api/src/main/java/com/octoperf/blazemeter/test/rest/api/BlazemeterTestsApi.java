package com.octoperf.blazemeter.test.rest.api;

import com.octoperf.blazemeter.search.entity.SearchListResult;
import com.octoperf.blazemeter.test.entity.TestDetailResult;
import com.octoperf.blazemeter.test.entity.TestFile;
import com.octoperf.blazemeter.test.entity.TestListItem;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BlazemeterTestsApi {

  @GET("/api/v4/tests")
  Call<SearchListResult<TestListItem>> tests(@Query("projectId") long projectId,
                                             @Query("platform") String platform,
                                             @Query("skip") int skip,
                                             @Query("limit") int limit);

  @GET("/api/v4/tests/{testId}/files")
  Call<SearchListResult<TestFile>> testFiles(@Path("testId") int testId);

  @GET("/api/v4/tests/{testId}")
  Call<TestDetailResult> test(@Path("testId") int testId);
}
