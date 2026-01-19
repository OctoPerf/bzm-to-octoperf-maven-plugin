package com.octoperf.workspace.rest.api;

import com.octoperf.workspace.entity.Workspace;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface OctoperfWorkspacesApi {

  @POST("/workspaces")
  Call<Workspace> create(@Body Workspace workspace);

  @GET("/workspaces/member-of")
  Call<List<Workspace>> workspaces();
}
