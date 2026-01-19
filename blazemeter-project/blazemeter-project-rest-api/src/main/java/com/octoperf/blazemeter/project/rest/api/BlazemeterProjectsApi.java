package com.octoperf.blazemeter.project.rest.api;

import com.octoperf.blazemeter.project.entity.BlazemeterProject;
import com.octoperf.blazemeter.search.entity.SearchListResult;
import com.octoperf.blazemeter.search.entity.SearchRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BlazemeterProjectsApi {

  @POST("/api/v4/search")
  Call<SearchListResult<BlazemeterProject>> search(@Body SearchRequest search);
}
