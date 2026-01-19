package com.octoperf.maven.api;


import com.octoperf.workspace.entity.Workspace;

import java.util.List;

public interface OctoperfWorkspaces {

  Workspace createWorkspace(String name);

  List<Workspace> getWorkspaces();
}
