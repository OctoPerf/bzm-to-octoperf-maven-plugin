package com.octoperf.maven.rest;

import com.octoperf.blazemeter.search.entity.SearchListResult;
import com.octoperf.blazemeter.search.helper.BlazemeterSearchRestHelper;
import com.octoperf.blazemeter.workspace.entity.BlazemeterWorkspace;
import com.octoperf.blazemeter.workspace.rest.api.BlazemeterWorkspacesApi;
import com.octoperf.maven.api.BlazemeterWorkspaces;
import com.octoperf.tools.retrofit.CallService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.octoperf.blazemeter.search.entity.SearchEntityField.WORKSPACE;
import static com.octoperf.blazemeter.search.entity.SearchEntityField.newSearchRequest;
import static java.util.List.of;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class BlazemeterRestWorkspaces implements BlazemeterWorkspaces {
  @NonNull
  BlazemeterWorkspacesApi api;
  @NonNull
  BlazemeterSearchRestHelper searchHelper;
  @NonNull
  CallService calls;

  @Override
  public List<BlazemeterWorkspace> getWorkspaces() {
    return searchHelper.searchAll((skip, limit) ->
      calls.execute(api.search(newSearchRequest(WORKSPACE, skip, limit)))
        .map(SearchListResult::getResult)
        .orElse(of()));
  }
}
