package com.octoperf.blazemeter.workspace.rest.api;

import com.octoperf.blazemeter.search.entity.SearchListResult;
import com.octoperf.blazemeter.search.entity.SearchRequest;
import com.octoperf.blazemeter.workspace.entity.BlazemeterWorkspace;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BlazemeterWorkspacesApi {

  @POST("/api/v4/search")
  Call<SearchListResult<BlazemeterWorkspace>> search(@Body SearchRequest search);
}
