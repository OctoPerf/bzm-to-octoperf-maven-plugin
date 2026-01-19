package com.octoperf.tools.retrofit.security.octoperf;

import com.octoperf.tools.retrofit.RestApiWrapper;

public interface OctoperfApiWrapper extends RestApiWrapper {

  OctoperfInterceptor authenticator();
}