package com.octoperf.blazemeter.test.entity;

import com.google.common.testing.NullPointerTester;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestDetailTest {

  @Test
  void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(TestDetail.class).verify();
  }

  @Test
  void shouldPassNullPointerTester() {
    new NullPointerTester().testConstructors(TestDetail.class, PACKAGE);
  }

  @Test
  void shouldCreate() {
    assertNotNull(newInstance());
  }

  @Test
  void shouldHaveNonStandardToString() {
    assertFalse(newInstance().toString().contains("@"));
  }

  public static TestDetail newInstance() {
    return TestDetail
      .builder()
      .id(2)
      .name("name")
      .dependencies(of(TestDependenciesTest.newInstance()))
      .build();
  }
}