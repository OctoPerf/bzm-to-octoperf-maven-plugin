package com.octoperf.maven.rest;


import com.octoperf.design.rest.api.OctoperfVariablesApi;
import com.octoperf.entity.design.VariableWrapper;
import com.octoperf.maven.api.OctoperfVariables;
import com.octoperf.tools.retrofit.CallService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class OctoperfRestVariables implements OctoperfVariables {
  OctoperfVariablesApi api;
  CallService calls;

  @Override
  public VariableWrapper createVariable(final VariableWrapper variable) {
    return calls.execute(api.createVariable(variable))
      .orElseThrow(() -> new RuntimeException("Unable to create variable '" + variable.getName() + "'"));
  }
}
