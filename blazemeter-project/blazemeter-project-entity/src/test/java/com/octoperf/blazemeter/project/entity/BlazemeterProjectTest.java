package com.octoperf.blazemeter.project.entity;

import com.google.common.testing.NullPointerTester;
import com.octoperf.blazemeter.project.entity.BlazemeterProject.BlazemeterProjectBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BlazemeterProjectTest {

  @Test
  void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(BlazemeterProject.class).verify();
  }

  @Test
  void shouldPassNullPointerTester() {
    new NullPointerTester().testConstructors(BlazemeterProject.class, PACKAGE);
  }

  @Test
  void shouldCreate() {
    assertNotNull(newInstance());
  }

  @Test
  void shouldHaveNonStandardToString() {
    assertFalse(newInstance().toString().contains("@"));
  }

  public static BlazemeterProject newInstance() {
    return newBuilder().build();
  }

  public static BlazemeterProjectBuilder newBuilder() {
    return BlazemeterProject
      .builder()
      .id(1)
      .workspaceId(2)
      .name("name");
  }
}