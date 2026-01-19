package com.octoperf.blazemeter.test.entity;

import com.google.common.testing.NullPointerTester;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestPluginsTest {
  @Test
  void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(TestPlugins.class).verify();
  }

  @Test
  void shouldPassNullPointerTester() {
    new NullPointerTester()
      .testConstructors(TestPlugins.class, PACKAGE);
  }

  @Test
  void shouldCreate() {
    assertNotNull(newInstance());
  }

  @Test
  void shouldHaveNonStandardToString() {
    assertFalse(newInstance().toString().contains("@"));
  }

  public static TestPlugins newInstance() {
    return TestPlugins
      .builder()
      .sharedFolders(of("sharedFolder"))
      .build();
  }
}