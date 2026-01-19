package com.octoperf.blazemeter.file.rest.api;

import com.octoperf.blazemeter.file.entity.SharedFolderListResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BlazemeterFilesApi {

  @GET("/api/v4/folders/{folderId}/files")
  Call<SharedFolderListResult> getFiles(@Path("folderId") String folderId);
}
