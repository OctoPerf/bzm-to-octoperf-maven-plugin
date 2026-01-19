package com.octoperf.entity.design;

import com.google.common.testing.NullPointerTester;
import com.octoperf.entity.design.Project.ProjectBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static com.octoperf.entity.design.ProjectType.DESIGN;
import static java.util.Set.of;
import static org.joda.time.DateTimeZone.UTC;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProjectTest {

  @Test
  void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(Project.class).verify();
  }

  @Test
  void shouldPassNullPointerTester() {
    new NullPointerTester().testConstructors(Project.class, PACKAGE);
  }

  @Test
  void shouldCreate() {
    assertNotNull(newInstance());
  }

  @Test
  void shouldHaveNonStandardToString() {
    assertFalse(newInstance().toString().contains("@"));
  }

  public static Project newInstance() {
    return newBuilder().build();
  }

  public static ProjectBuilder newBuilder() {
    final DateTime now = DateTime.now(UTC);
    return Project
      .builder()
      .workspaceId("workspaceId")
      .id("projectId")
      .userId("")
      .name("name")
      .description("description")
      .type(DESIGN)
      .tags(of("tag"))
      .created(now)
      .lastModified(now);
  }
}
