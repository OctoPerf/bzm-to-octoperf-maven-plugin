package com.octoperf.tools.retrofit.security.blazemeter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import okhttp3.Response;

import java.io.IOException;

import static com.google.common.net.HttpHeaders.ACCEPT;
import static com.google.common.net.HttpHeaders.ORIGIN;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class TdmInterceptor implements BlazemeterInterceptor {
  private static final String TDM_ACCEPT = "text/csv";
  private static final String TDM_ORIGIN = "https://a.blazemeter.com";

  @Override
  public @NonNull Response intercept(final Chain chain) throws IOException {
    return chain.proceed(
      chain
        .request()
        .newBuilder()
        .addHeader(ACCEPT, TDM_ACCEPT)
        .addHeader(ORIGIN, TDM_ORIGIN)
        .build()
    );
  }
}

