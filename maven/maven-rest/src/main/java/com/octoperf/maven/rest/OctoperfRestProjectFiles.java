package com.octoperf.maven.rest;

import com.octoperf.design.rest.api.OctoperfProjectFilesApi;
import com.octoperf.maven.api.OctoperfProjectFiles;
import com.octoperf.tools.retrofit.CallService;
import com.octoperf.tools.retrofit.security.blazemeter.BlazemeterLinkClientFactory;
import lombok.experimental.FieldDefaults;
import okhttp3.*;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

@Component
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class OctoperfRestProjectFiles implements OctoperfProjectFiles {
  OctoperfProjectFilesApi api;
  OkHttpClient bzmLinkClient;
  CallService calls;

  OctoperfRestProjectFiles(final OctoperfProjectFilesApi api,
                           final BlazemeterLinkClientFactory linkClientFactory,
                           final CallService calls) {
    this.api = requireNonNull(api);
    this.bzmLinkClient = requireNonNull(linkClientFactory).newLinkClient();
    this.calls = requireNonNull(calls);
  }

  @Override
  public void upload(final String projectId, final String filename, final String fileLink) throws IOException {
    final Request request = new Request.Builder()
      .url(fileLink)
      .build();

    try (final Response response = bzmLinkClient.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        throw new IOException("Download failed: " + response);
      }
      final ResponseBody body = response.body();
      final InputStream inputStream = requireNonNull(body).byteStream();
      upload(projectId, filename, inputStream, body.contentType());
    }
  }

  @Override
  public void upload(final String projectId,
                     final String filename,
                     final InputStream inputStream,
                     final MediaType mediaType) {

    final RequestBody streamingBody = new RequestBody() {
      @Override
      public MediaType contentType() {
        return mediaType;
      }

      @Override
      public void writeTo(final BufferedSink sink) throws IOException {
        try (final Source source = Okio.source(inputStream)) {
          sink.writeAll(source);
        }
      }
    };

    final MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", filename, streamingBody);
    calls.execute(api.upload(projectId, filename, filePart));
  }
}
