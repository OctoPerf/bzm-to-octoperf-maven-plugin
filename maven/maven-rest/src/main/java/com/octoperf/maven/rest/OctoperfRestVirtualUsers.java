package com.octoperf.maven.rest;


import com.octoperf.design.rest.api.OctoperfImportApi;
import com.octoperf.entity.design.JmxDesignImportRequest;
import com.octoperf.entity.design.JmxImportResult;
import com.octoperf.maven.api.OctoperfVirtualUsers;
import com.octoperf.tools.jackson.mapper.JsonMapperService;
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

import static com.octoperf.entity.design.AdBlocking.ENABLED;
import static com.octoperf.entity.design.HtmlResources.KEEP_ALL;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;
import static okhttp3.MediaType.parse;
import static okhttp3.RequestBody.create;

@Component
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class OctoperfRestVirtualUsers implements OctoperfVirtualUsers {
  OctoperfImportApi api;
  OkHttpClient bzmLinkClient;
  CallService calls;
  JsonMapperService mapper;

  OctoperfRestVirtualUsers(final OctoperfImportApi api,
                           final BlazemeterLinkClientFactory linkClientFactory,
                           final CallService calls,
                           final JsonMapperService mapper) {
    this.api = requireNonNull(api);
    this.bzmLinkClient = requireNonNull(linkClientFactory).newLinkClient();
    this.calls = requireNonNull(calls);
    this.mapper = requireNonNull(mapper);
  }

  @Override
  public JmxImportResult importJmx(final String projectId, final String filename, final String fileLink) throws IOException {
    final Request request = new Request.Builder()
      .url(fileLink)
      .build();

    try (final Response response = bzmLinkClient.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        throw new IOException("Download failed: " + response);
      }
      final ResponseBody body = response.body();
      final InputStream inputStream = requireNonNull(body).byteStream();

      final RequestBody streamingBody = new RequestBody() {
        @Override
        public MediaType contentType() {
          return body.contentType();
        }

        @Override
        public void writeTo(BufferedSink sink) throws IOException {
          try (Source source = Okio.source(inputStream)) {
            sink.writeAll(source);
          }
        }
      };

      final MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", filename, streamingBody);
      final JmxDesignImportRequest design = JmxDesignImportRequest.builder()
        .resources(KEEP_ALL)
        .adBlocking(ENABLED)
        .build();
      final RequestBody designBody = create(
        mapper.toJson(design),
        parse("application/json")
      );
      return calls.execute(api.importJmx(projectId, designBody, filePart))
        .orElseThrow(() -> new IOException("Unable to import JMX file '" + filename + "'"));
    }
  }
}
