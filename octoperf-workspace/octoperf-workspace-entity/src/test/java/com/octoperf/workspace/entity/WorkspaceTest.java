package com.octoperf.workspace.entity;

import com.google.common.testing.NullPointerTester;
import com.octoperf.workspace.entity.Workspace.WorkspaceBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static org.joda.time.DateTimeZone.UTC;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WorkspaceTest {

  @Test
  void shouldPassNPE() {
    final Workspace entity = create();
    new NullPointerTester()
      .setDefault(Optional.class, Optional.empty())
      .testConstructors(entity.getClass(), PACKAGE);
  }

  @Test
  void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(Workspace.class).verify();
  }

  @Test
  void shouldPassNullPointerTester() {
    new NullPointerTester().testConstructors(Workspace.class, PACKAGE);
  }

  @Test
  void shouldCreate() {
    assertNotNull(create());
  }

  @Test
  void shouldHaveNonStandardToString() {
    assertFalse(create().toString().contains("@"));
  }

  protected Workspace create() {
    return newInstance();
  }

  public static Workspace newInstance() {
    return newBuilder().build();
  }

  public static WorkspaceBuilder newBuilder() {
    final DateTime now = DateTime.now(UTC);
    return Workspace
      .builder()
      .id("workspaceId")
      .userId("")
      .description("")
      .name("wName")
      .created(now)
      .lastModified(now);
  }
}
