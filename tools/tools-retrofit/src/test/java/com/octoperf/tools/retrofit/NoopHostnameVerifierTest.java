package com.octoperf.tools.retrofit;

import org.junit.jupiter.api.Test;

import static com.octoperf.tools.retrofit.NoopHostnameVerifier.INSTANCE;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NoopHostnameVerifierTest {

  @Test
  void shouldNotVerify() {
    assertTrue(INSTANCE.verify("", null));
  }
}
