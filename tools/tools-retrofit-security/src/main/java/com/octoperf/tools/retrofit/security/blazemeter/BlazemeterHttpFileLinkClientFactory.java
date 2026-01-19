package com.octoperf.tools.retrofit.security.blazemeter;

import com.octoperf.tools.retrofit.NoopHostnameVerifier;
import com.octoperf.tools.retrofit.security.log.ErrorLoggingInterceptor;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import okhttp3.OkHttpClient;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import static java.util.concurrent.TimeUnit.MINUTES;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class BlazemeterHttpFileLinkClientFactory implements BlazemeterLinkClientFactory {
  @NonNull
  X509TrustManager trustManager;
  @NonNull
  SSLSocketFactory socketFactory;

  @Override
  public OkHttpClient newLinkClient() {
    return new OkHttpClient
      .Builder()
      .readTimeout(1, MINUTES)
      .writeTimeout(1, MINUTES)
      .hostnameVerifier(new NoopHostnameVerifier())
      .addNetworkInterceptor(new ErrorLoggingInterceptor())
      .sslSocketFactory(socketFactory, trustManager)
      .build();
  }
}
