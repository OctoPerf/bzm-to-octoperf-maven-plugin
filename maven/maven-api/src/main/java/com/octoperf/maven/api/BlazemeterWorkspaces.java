package com.octoperf.maven.api;

import com.octoperf.blazemeter.workspace.entity.BlazemeterWorkspace;

import java.util.List;

@FunctionalInterface
public interface BlazemeterWorkspaces {

  List<BlazemeterWorkspace> getWorkspaces();
}
