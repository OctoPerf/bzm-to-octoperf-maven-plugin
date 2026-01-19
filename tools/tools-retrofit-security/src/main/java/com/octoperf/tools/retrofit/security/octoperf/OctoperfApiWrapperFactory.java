package com.octoperf.tools.retrofit.security.octoperf;

@FunctionalInterface
public interface OctoperfApiWrapperFactory {

  OctoperfApiWrapper newWrapper(String baseUrl, String apiKey);
}
