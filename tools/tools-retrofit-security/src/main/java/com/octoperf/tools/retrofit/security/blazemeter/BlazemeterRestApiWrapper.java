package com.octoperf.tools.retrofit.security.blazemeter;

import com.octoperf.tools.retrofit.RestApiWrapper;
import com.octoperf.tools.retrofit.security.octoperf.OctoperfInterceptor;

public interface BlazemeterRestApiWrapper extends RestApiWrapper {

  OctoperfInterceptor authenticator();
}