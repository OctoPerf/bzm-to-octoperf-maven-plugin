package com.octoperf.blazemeter.test.entity;

import com.google.common.testing.NullPointerTester;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestDetailResultTest {

  @Test
  void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(TestDetailResult.class).verify();
  }

  @Test
  void shouldPassNullPointerTester() {
    new NullPointerTester().testConstructors(TestDetailResult.class, PACKAGE);
  }

  @Test
  void shouldCreate() {
    assertNotNull(newInstance());
  }

  @Test
  void shouldHaveNonStandardToString() {
    assertFalse(newInstance().toString().contains("@"));
  }

  public static TestDetailResult newInstance() {
    return new TestDetailResult(TestDetailTest.newInstance());
  }
}