package com.octoperf.blazemeter.test.entity;

import com.google.common.testing.NullPointerTester;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestConfigurationTest {
  @Test
  void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(TestConfiguration.class).verify();
  }

  @Test
  void shouldPassNullPointerTester() {
    new NullPointerTester()
      .setDefault(TestPlugins.class, TestPluginsTest.newInstance())
      .testConstructors(TestConfiguration.class, PACKAGE);
  }

  @Test
  void shouldCreate() {
    assertNotNull(newInstance());
  }

  @Test
  void shouldHaveNonStandardToString() {
    assertFalse(newInstance().toString().contains("@"));
  }

  public static TestConfiguration newInstance() {
    return TestConfiguration
      .builder()
      .filename("filename")
      .scriptType("scriptType")
      .plugins(TestPluginsTest.newInstance())
      .build();
  }
}