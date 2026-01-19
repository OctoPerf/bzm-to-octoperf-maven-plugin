package com.octoperf.tools.retrofit.security.blazemeter;

public interface BlazemeterApiWrapperFactory {

  BlazemeterStdApiWrapper newStdApiWrapper(String apiKeyId, String apiKeySecret);

  BlazemeterTdmApiWrapper newTdmApiWrapper(String apiKeyId, String apiKeySecret);
}
