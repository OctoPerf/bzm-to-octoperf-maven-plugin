package com.octoperf.maven.rest;

import com.octoperf.blazemeter.project.entity.BlazemeterProject;
import com.octoperf.blazemeter.project.rest.api.BlazemeterProjectsApi;
import com.octoperf.blazemeter.search.entity.SearchListResult;
import com.octoperf.blazemeter.search.helper.BlazemeterSearchRestHelper;
import com.octoperf.maven.api.BlazemeterProjects;
import com.octoperf.tools.retrofit.CallService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.octoperf.blazemeter.search.entity.SearchEntityField.PROJECT;
import static com.octoperf.blazemeter.search.entity.SearchEntityField.newSearchRequest;
import static java.util.List.of;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class BlazemeterRestProjects implements BlazemeterProjects {
  @NonNull
  BlazemeterProjectsApi api;
  @NonNull
  BlazemeterSearchRestHelper searchHelper;
  @NonNull
  CallService calls;

  @Override
  public List<BlazemeterProject> getProjects() {
    return searchHelper.searchAll((skip, limit) ->
      calls.execute(api.search(newSearchRequest(PROJECT, skip, limit)))
        .map(SearchListResult::getResult)
        .orElse(of()));
  }
}
