package com.octoperf.design.rest.api;

import com.octoperf.entity.design.JmxImportResult;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface OctoperfImportApi {

  @Multipart
  @POST("/imports/jmx/{projectId}")
  Call<JmxImportResult> importJmx(@Path("projectId") String projectId,
                                  @Part("design") RequestBody design,
                                  @Part MultipartBody.Part file);
}
