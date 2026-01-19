package com.octoperf.blazemeter.migration;

import com.google.common.testing.NullPointerTester;
import com.octoperf.blazemeter.migration.BlazemeterToOctoperfMigrationRequest.BlazemeterToOctoperfMigrationRequestBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BlazemeterToOctoperfMigrationRequestTest {

  @Test
  void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(BlazemeterToOctoperfMigrationRequest.class).verify();
  }

  @Test
  void shouldPassNullPointerTester() {
    new NullPointerTester().testConstructors(BlazemeterToOctoperfMigrationRequest.class, PACKAGE);
  }

  @Test
  void shouldCreate() {
    assertNotNull(newInstance());
  }

  @Test
  void shouldHaveNonStandardToString() {
    assertFalse(newInstance().toString().contains("@"));
  }

  public static BlazemeterToOctoperfMigrationRequest newInstance() {
    return newBuilder().build();
  }

  public static BlazemeterToOctoperfMigrationRequestBuilder newBuilder() {
    return BlazemeterToOctoperfMigrationRequest
      .builder()
      .bzmWorkspaceId(Optional.of(1))
      .bzmProjectId(Optional.of(2))
      .alwaysCreateNewWorkspace(true)
      .alwaysCreateNewProject(false);
  }
}