package com.octoperf.blazemeter.test.rest.api;

import com.octoperf.blazemeter.test.entity.TestDataFileRequest;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BlazemeterTestDataFilesApi {

  @POST("/api/v1/workspaces/{workspaceId}/testdata/generatefile")
  Call<ResponseBody> generateFile(@Path("workspaceId") int workspaceId,
                                  @Body TestDataFileRequest body);
}
