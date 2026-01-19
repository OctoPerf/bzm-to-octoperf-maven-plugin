package com.octoperf.design.rest.api;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Set;

public interface OctoperfProjectFilesApi {

  @GET("/design/project-files/list/{projectId}")
  Call<Set<String>> listFiles(@Path("projectId") String projectId);

  @Multipart
  @POST("/design/project-files/{projectId}")
  Call<Void> upload(@Path("projectId") String projectId,
                    @Part("filename") String filename,
                    @Part MultipartBody.Part file);
}
