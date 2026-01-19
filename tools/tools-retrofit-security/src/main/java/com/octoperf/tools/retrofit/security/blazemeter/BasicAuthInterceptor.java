package com.octoperf.tools.retrofit.security.blazemeter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import okhttp3.Credentials;
import okhttp3.Response;

import java.io.IOException;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class BasicAuthInterceptor implements BlazemeterInterceptor {
  @NonNull
  String basicAuth;

  BasicAuthInterceptor(final String blazemeterApiKeyId,
                       final String blazemeterApiKeySecret) {
    this.basicAuth = Credentials.basic(
      requireNonNull(blazemeterApiKeyId),
      requireNonNull(blazemeterApiKeySecret)
    );
  }

  @Override
  public @NonNull Response intercept(final Chain chain) throws IOException {
    return chain.proceed(
      chain
        .request()
        .newBuilder()
        .addHeader(AUTHORIZATION, basicAuth)
        .build()
    );
  }
}

