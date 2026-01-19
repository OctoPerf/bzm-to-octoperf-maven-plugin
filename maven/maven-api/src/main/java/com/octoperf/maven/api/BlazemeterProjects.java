package com.octoperf.maven.api;

import com.octoperf.blazemeter.project.entity.BlazemeterProject;

import java.util.List;

@FunctionalInterface
public interface BlazemeterProjects {

  List<BlazemeterProject> getProjects();
}
