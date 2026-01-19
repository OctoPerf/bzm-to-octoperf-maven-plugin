package com.octoperf.maven.api;

import okhttp3.MediaType;

import java.io.IOException;
import java.io.InputStream;

public interface OctoperfProjectFiles {

  void upload(String projectId, String filename, String fileLink) throws IOException;

  void upload(String projectId, String filename, InputStream inputStream, MediaType mediaType);
}
