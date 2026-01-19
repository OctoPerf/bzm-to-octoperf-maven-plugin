package com.octoperf.tools.retrofit.security.octoperf;

import com.octoperf.tools.retrofit.LoggingConverterFactory;
import com.octoperf.tools.retrofit.NoopHostnameVerifier;
import com.octoperf.tools.retrofit.security.log.ErrorLoggingInterceptor;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import static java.util.concurrent.TimeUnit.MINUTES;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class RetrofitApiWrapperFactory implements OctoperfApiWrapperFactory {
  @NonNull
  LoggingConverterFactory converterFactory;
  @NonNull
  X509TrustManager trustManager;
  @NonNull
  SSLSocketFactory socketFactory;

  @Override
  public OctoperfApiWrapper newWrapper(final String baseUrl, final String apiKey) {
    final ApiKeyInterceptor interceptor = new ApiKeyInterceptor(apiKey);
    final Interceptor errorInterceptor = new ErrorLoggingInterceptor();

    final OkHttpClient.Builder builder = new OkHttpClient
      .Builder()
      .readTimeout(1, MINUTES)
      .writeTimeout(1, MINUTES)
      .addNetworkInterceptor(interceptor)
      .addNetworkInterceptor(errorInterceptor)
      .hostnameVerifier(new NoopHostnameVerifier())
      .sslSocketFactory(socketFactory, trustManager);

    return new RetrofitApiWrapper(new Retrofit
      .Builder()
      .addConverterFactory(converterFactory.newConverterFactory())
      .client(builder.build())
      .baseUrl(baseUrl)
      .build(), interceptor);
  }
}
