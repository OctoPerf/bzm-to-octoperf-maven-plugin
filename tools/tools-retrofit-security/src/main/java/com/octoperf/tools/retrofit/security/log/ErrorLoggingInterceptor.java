package com.octoperf.tools.retrofit.security.log;

import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.Buffer;
import org.jspecify.annotations.NonNull;

import java.io.IOException;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ErrorLoggingInterceptor implements Interceptor {
  static final long RESPONSE_MAX_SIZE = 10_000;

  @Override
  public @NonNull Response intercept(final Chain chain) throws IOException {
    final Request req = chain.request();
    final Response res = chain.proceed(req);

    if (res.isSuccessful()) {
      return res;
    }

    log.error("HTTP ERROR {} {}\nRequest headers: {}\nRequest body: {}\nResponse headers: {}\nResponse body: {}",
      res.code(),
      req.url(),
      req.headers().newBuilder().removeAll(AUTHORIZATION).build(),
      extractRequestBody(req),
      res.headers(),
      isNull(res.body()) ? "" : res.peekBody(RESPONSE_MAX_SIZE).string()
    );

    return res;
  }

  static String extractRequestBody(final Request req) throws IOException {
    final RequestBody body = req.body();
    if (nonNull(body) && isPlainText(body)) {
      final Buffer buffer = new Buffer();
      body.writeTo(buffer);
      return buffer.readUtf8();
    }
    return "";
  }

  static boolean isPlainText(final RequestBody body) {
    final MediaType mt = body.contentType();
    if (isNull(mt)) {
      return false;
    }
    final String type = mt.type().toLowerCase();
    final String subtype = mt.subtype().toLowerCase();
    return "text".equals(type)
      || subtype.contains("json")
      || subtype.contains("xml")
      || "csv".equals(subtype);
  }
}
