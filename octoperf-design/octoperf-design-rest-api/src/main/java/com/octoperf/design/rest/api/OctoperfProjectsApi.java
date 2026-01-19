package com.octoperf.design.rest.api;

import com.octoperf.entity.design.Project;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface OctoperfProjectsApi {

  @POST("/design/projects")
  Call<Project> create(@Body Project project);

  @GET("/design/projects/by-workspace/{workspaceId}/DESIGN")
  Call<List<Project>> projects(@Path("workspaceId") String workspaceId);
}
