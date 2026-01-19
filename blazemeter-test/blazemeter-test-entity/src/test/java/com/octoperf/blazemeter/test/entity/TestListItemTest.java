package com.octoperf.blazemeter.test.entity;

import com.google.common.testing.NullPointerTester;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestListItemTest {

  @Test
  void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(TestListItem.class).verify();
  }

  @Test
  void shouldPassNullPointerTester() {
    new NullPointerTester()
      .setDefault(TestConfiguration.class, TestConfigurationTest.newInstance())
      .testConstructors(TestListItem.class, PACKAGE);
  }

  @Test
  void shouldCreate() {
    assertNotNull(newInstance());
  }

  @Test
  void shouldHaveNonStandardToString() {
    assertFalse(newInstance().toString().contains("@"));
  }

  public static TestListItem newInstance() {
    return TestListItem
      .builder()
      .id(2)
      .projectId(3)
      .name("name")
      .configuration(TestConfigurationTest.newInstance())
      .build();
  }
}