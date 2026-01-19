package com.octoperf.maven.api;

import com.octoperf.entity.design.Project;

import java.util.List;

public interface OctoperfProjects {

  Project createProject(String workspaceId, String name);

  List<Project> projects(String workspaceId);
}
