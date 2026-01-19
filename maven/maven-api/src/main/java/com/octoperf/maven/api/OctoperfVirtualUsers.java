package com.octoperf.maven.api;

import com.octoperf.entity.design.JmxImportResult;

import java.io.IOException;

public interface OctoperfVirtualUsers {

  JmxImportResult importJmx(String projectId, String filename, String fileLink) throws IOException;
}
