package com.octoperf.maven.rest;

import com.octoperf.blazemeter.file.rest.api.BlazemeterFilesApi;
import com.octoperf.blazemeter.project.rest.api.BlazemeterProjectsApi;
import com.octoperf.blazemeter.test.rest.api.BlazemeterTestDataFilesApi;
import com.octoperf.blazemeter.test.rest.api.BlazemeterTestsApi;
import com.octoperf.blazemeter.workspace.rest.api.BlazemeterWorkspacesApi;
import com.octoperf.design.rest.api.OctoperfImportApi;
import com.octoperf.design.rest.api.OctoperfProjectFilesApi;
import com.octoperf.design.rest.api.OctoperfProjectsApi;
import com.octoperf.design.rest.api.OctoperfVariablesApi;
import com.octoperf.tools.retrofit.security.blazemeter.BlazemeterStdApiWrapper;
import com.octoperf.tools.retrofit.security.blazemeter.BlazemeterTdmApiWrapper;
import com.octoperf.tools.retrofit.security.octoperf.OctoperfApiWrapper;
import com.octoperf.workspace.rest.api.OctoperfWorkspacesApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RestMavenConfig {

  @Bean
  OctoperfProjectFilesApi projectFilesApi(final OctoperfApiWrapper w) {
    return w.create(OctoperfProjectFilesApi.class);
  }

  @Bean
  OctoperfProjectsApi projectsApi(final OctoperfApiWrapper w) {
    return w.create(OctoperfProjectsApi.class);
  }

  @Bean
  OctoperfImportApi importApi(final OctoperfApiWrapper w) {
    return w.create(OctoperfImportApi.class);
  }

  @Bean
  OctoperfVariablesApi variableApi(final OctoperfApiWrapper w) {
    return w.create(OctoperfVariablesApi.class);
  }

  @Bean
  OctoperfWorkspacesApi workspacesApi(final OctoperfApiWrapper w) {
    return w.create(OctoperfWorkspacesApi.class);
  }

  @Bean
  BlazemeterWorkspacesApi bzmWorkspacesApi(final BlazemeterStdApiWrapper w) {
    return w.create(BlazemeterWorkspacesApi.class);
  }

  @Bean
  BlazemeterProjectsApi bzmProjectsApi(final BlazemeterStdApiWrapper w) {
    return w.create(BlazemeterProjectsApi.class);
  }

  @Bean
  BlazemeterTestsApi bzmTestsApi(final BlazemeterStdApiWrapper w) {
    return w.create(BlazemeterTestsApi.class);
  }

  @Bean
  BlazemeterTestDataFilesApi bzmTestDataFiles(final BlazemeterTdmApiWrapper w) {
    return w.create(BlazemeterTestDataFilesApi.class);
  }

  @Bean
  BlazemeterFilesApi bzmFilesApi(final BlazemeterStdApiWrapper w) {
    return w.create(BlazemeterFilesApi.class);
  }
}
