package com.octoperf.tools.retrofit.security.blazemeter;

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
final class RetrofitApiWrapperFactory implements BlazemeterApiWrapperFactory {
  private static final String STD_BASE_URL = "https://a.blazemeter.com/";
  private static final String TDM_BASE_URL = "https://tdm.blazemeter.com/";

  @NonNull
  LoggingConverterFactory converterFactory;
  @NonNull
  X509TrustManager trustManager;
  @NonNull
  SSLSocketFactory socketFactory;

  @Override
  public BlazemeterStdApiWrapper newStdApiWrapper(final String apiKeyId, final String apiKeySecret) {
    return new StdRetrofitApiWrapper(buildRetrofit(
      STD_BASE_URL,
      new BasicAuthInterceptor(apiKeyId, apiKeySecret),
      new ErrorLoggingInterceptor()
    ));
  }

  @Override
  public BlazemeterTdmApiWrapper newTdmApiWrapper(String apiKeyId, String apiKeySecret) {
    return new TdmRetrofitApiWrapper(buildRetrofit(
      TDM_BASE_URL,
      new BasicAuthInterceptor(apiKeyId, apiKeySecret),
      new TdmInterceptor(),
      new ErrorLoggingInterceptor()
    ));
  }

  Retrofit buildRetrofit(final String baseUrl,
                         final Interceptor... interceptors) {
    final OkHttpClient.Builder builder = new OkHttpClient
      .Builder()
      .readTimeout(1, MINUTES)
      .writeTimeout(1, MINUTES)
      .hostnameVerifier(new NoopHostnameVerifier())
      .sslSocketFactory(socketFactory, trustManager);

    for (final Interceptor i : interceptors) {
      builder.addNetworkInterceptor(i);
    }

    return new Retrofit
      .Builder()
      .addConverterFactory(converterFactory.newConverterFactory())
      .client(builder.build())
      .baseUrl(baseUrl)
      .build();
  }
}
