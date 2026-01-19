package com.octoperf.maven.plugin;

import com.octoperf.tools.retrofit.security.blazemeter.BlazemeterApiWrapperFactory;
import com.octoperf.tools.retrofit.security.blazemeter.BlazemeterStdApiWrapper;
import com.octoperf.tools.retrofit.security.blazemeter.BlazemeterTdmApiWrapper;
import com.octoperf.tools.retrofit.security.octoperf.OctoperfApiWrapper;
import com.octoperf.tools.retrofit.security.octoperf.OctoperfApiWrapperFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MavenPluginConfig {

  @Bean
  OctoperfApiWrapper octoperfApiWrapper(
    @Value("${apiKey}") final String apiKey,
    @Value("${serverUrl}") final String serverUrl,
    final OctoperfApiWrapperFactory factory) {
    return factory.newWrapper(serverUrl, apiKey);
  }

  @Bean
  BlazemeterStdApiWrapper blazemeterStdApiWrapper(
    @Value("${blazemeterApiKeyId}") final String keyId,
    @Value("${blazemeterApiKeySecret}") final String keySecret,
    final BlazemeterApiWrapperFactory factory) {
    return factory.newStdApiWrapper(keyId, keySecret);
  }

  @Bean
  BlazemeterTdmApiWrapper blazemeterTdmApiWrapper(
    @Value("${blazemeterApiKeyId}") final String keyId,
    @Value("${blazemeterApiKeySecret}") final String keySecret,
    final BlazemeterApiWrapperFactory factory) {
    return factory.newTdmApiWrapper(keyId, keySecret);
  }
}
