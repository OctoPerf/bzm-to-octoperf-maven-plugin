package com.octoperf.tools.retrofit.security.octoperf;

import com.google.common.testing.NullPointerTester;
import com.octoperf.tools.retrofit.LoggingConverterFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import retrofit2.Converter.Factory;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.quality.Strictness.LENIENT;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = LENIENT)
class RetrofitApiWrapperFactoryTest {

  @Mock
  LoggingConverterFactory converterFactory;
  @Mock
  X509TrustManager trustManager;
  @Mock
  SSLSocketFactory socketFactory;

  RetrofitApiWrapperFactory wrapperFactory;

  @BeforeEach
  void before() {
    when(converterFactory.newConverterFactory()).thenReturn(new Factory() {});
    when(trustManager.getAcceptedIssuers()).thenReturn(new X509Certificate[0]);
    wrapperFactory = new RetrofitApiWrapperFactory(converterFactory, trustManager, socketFactory);
  }

  @Test
  void shouldPassNPETester() {
    new NullPointerTester()
      .setDefault(SSLSocketFactory.class, (SSLSocketFactory) SSLSocketFactory.getDefault())
      .testConstructors(wrapperFactory.getClass(), PACKAGE);
  }

  @Test
  void shouldCreateWrapper() {
    assertNotNull(wrapperFactory.newWrapper("http://localhost", "apiKey"));
  }
}
