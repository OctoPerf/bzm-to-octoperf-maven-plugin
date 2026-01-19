package com.octoperf.maven.plugin;

import com.octoperf.Application;
import com.octoperf.blazemeter.migration.BlazemeterToOctoperfMigrationRequest;
import com.octoperf.blazemeter.migration.BlazemeterToOctoperfMigrationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.util.Optional;

@Slf4j
@Mojo(name = "migrate")
public class MigrateMojo extends AbstractMojo {

  @Parameter(property = "octoPerfServerUrl", defaultValue = "https://api.octoperf.com")
  String octoPerfServerUrl = "";
  @Parameter(property = "octoPerfApiKey", required = true)
  String octoPerfApiKey = "";

  @Parameter(property = "blazemeterApiKeyId", required = true)
  String blazemeterApiKeyId = "";
  @Parameter(property = "blazemeterApiKeySecret", required = true)
  String blazemeterApiKeySecret = "";

  @Parameter(property = "alwaysCreateNewWorkspace")
  boolean alwaysCreateNewWorkspace;
  @Parameter(property = "alwaysCreateNewProject")
  boolean alwaysCreateNewProject;
  @Parameter(property = "bzmWorkspaceId")
  int bzmWorkspaceId;
  @Parameter(property = "bzmProjectId")
  int bzmProjectId;

  @Override
  public void execute() {
    final GenericApplicationContext context = newContext();
    final BlazemeterToOctoperfMigrationService migration = context.getBean(BlazemeterToOctoperfMigrationService.class);
    migration.migrate(
      BlazemeterToOctoperfMigrationRequest.builder()
        .bzmWorkspaceId(Optional.of(bzmWorkspaceId).filter(id -> id > 0))
        .bzmProjectId(Optional.of(bzmProjectId).filter(id -> id > 0))
        .alwaysCreateNewWorkspace(alwaysCreateNewWorkspace)
        .alwaysCreateNewProject(alwaysCreateNewProject)
        .build()
    );
  }

  private GenericApplicationContext newContext() {
    System.setProperty("apiKey", octoPerfApiKey);
    System.setProperty("serverUrl", octoPerfServerUrl);
    System.setProperty("blazemeterApiKeyId", blazemeterApiKeyId);
    System.setProperty("blazemeterApiKeySecret", blazemeterApiKeySecret);
    final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
    context.register(Application.class);
    context.refresh();
    return context;
  }
}
