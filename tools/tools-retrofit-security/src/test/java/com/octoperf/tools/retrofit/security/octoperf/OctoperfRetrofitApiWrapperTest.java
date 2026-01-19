package com.octoperf.tools.retrofit.security.octoperf;

import com.google.common.testing.NullPointerTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import retrofit2.Retrofit;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.quality.Strictness.LENIENT;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = LENIENT)
class OctoperfRetrofitApiWrapperTest {

  @Mock
  OctoperfInterceptor authenticator;

  Retrofit retrofit;
  RetrofitApiWrapper wrapper;

  @BeforeEach
  void before() {
    retrofit = new Retrofit.Builder().baseUrl("http://localhost").build();
    wrapper = new RetrofitApiWrapper(retrofit, authenticator);
  }

  @Test
  void shouldPassNPETester() {
    new NullPointerTester()
      .setDefault(Retrofit.class, retrofit)
      .testConstructors(wrapper.getClass(), PACKAGE);
  }

  @Test
  void shouldCreateRestApi() {
    assertNotNull(wrapper.create(TestApi.class));
  }

  @Test
  void shouldReturnAuthenticator() {
    assertNotNull(wrapper.authenticator());
  }
}
