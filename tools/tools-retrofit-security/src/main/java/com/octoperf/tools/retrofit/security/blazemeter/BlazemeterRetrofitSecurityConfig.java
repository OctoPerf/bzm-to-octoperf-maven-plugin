package com.octoperf.tools.retrofit.security.blazemeter;

import com.octoperf.tools.retrofit.LoggingConverterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

@Configuration
class BlazemeterRetrofitSecurityConfig {

  @Bean
  BlazemeterApiWrapperFactory blazemeterApiWrapperFactory(
    final LoggingConverterFactory converterFactory,
    final X509TrustManager trustManager,
    final SSLSocketFactory socketFactory) {
    return new RetrofitApiWrapperFactory(
      converterFactory,
      trustManager,
      socketFactory
    );
  }

  @Bean
  BlazemeterLinkClientFactory blazemeterLinkClientFactory(
    final X509TrustManager trustManager,
    final SSLSocketFactory socketFactory) {
    return new BlazemeterHttpFileLinkClientFactory(
      trustManager,
      socketFactory
    );
  }
}
