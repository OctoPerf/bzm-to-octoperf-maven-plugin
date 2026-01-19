package com.octoperf.design.rest.api;

import com.octoperf.entity.design.VariableWrapper;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OctoperfVariablesApi {

  @POST("/design/variables")
  Call<VariableWrapper> createVariable(@Body VariableWrapper variable);
}
