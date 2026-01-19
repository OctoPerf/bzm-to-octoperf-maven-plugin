package com.octoperf.tools.retrofit;

import org.junit.jupiter.api.Test;

class TrustingX509TrustManagerTest {

  @Test
  void shouldCheck() {
    final TrustingX509TrustManager trustManager = new TrustingX509TrustManager();
    trustManager.getAcceptedIssuers();
    trustManager.checkClientTrusted(null, null);
    trustManager.checkServerTrusted(null, null);
  }
}
