package com.octoperf.tools.retrofit.security.blazemeter;

import okhttp3.OkHttpClient;

@FunctionalInterface
public interface BlazemeterLinkClientFactory {

  OkHttpClient newLinkClient();
}
