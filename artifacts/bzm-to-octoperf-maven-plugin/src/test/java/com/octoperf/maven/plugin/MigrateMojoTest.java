package com.octoperf.maven.plugin;

import com.octoperf.Application;
import com.octoperf.blazemeter.migration.BlazemeterToOctoperfMigrationRequest;
import com.octoperf.blazemeter.migration.BlazemeterToOctoperfMigrationService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static java.util.Optional.empty;


class MigrateMojoTest {

  @Test
  @SuppressWarnings("java:S2699")
  @Disabled("Only for manual debugging")
  void manuallyTestMigration() {
    System.setProperty("apiKey", "XXX");
    System.setProperty("serverUrl", "http://192.168.1.42:8090");
    System.setProperty("blazemeterApiKeyId", "YYY");
    System.setProperty("blazemeterApiKeySecret", "ZZZ");
    final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
    context.register(Application.class);
    context.refresh();

    final BlazemeterToOctoperfMigrationService migration = context.getBean(BlazemeterToOctoperfMigrationService.class);

    migration.migrate(
      BlazemeterToOctoperfMigrationRequest.builder()
        .bzmWorkspaceId(empty())
        .bzmProjectId(empty())
        .alwaysCreateNewWorkspace(true)
        .alwaysCreateNewProject(true)
        .build()
    );
  }
}