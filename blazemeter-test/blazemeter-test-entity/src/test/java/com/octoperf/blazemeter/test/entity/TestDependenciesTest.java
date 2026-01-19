package com.octoperf.blazemeter.test.entity;

import com.fasterxml.jackson.databind.node.LongNode;
import com.google.common.testing.NullPointerTester;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;

class TestDependenciesTest {

  @Test
  void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(TestDependencies.class).verify();
  }

  @Test
  void shouldPassNullPointerTester() {
    new NullPointerTester().testConstructors(TestDependencies.class, PACKAGE);
  }

  @Test
  void shouldCreate() {
    assertNotNull(newInstance());
  }

  @Test
  void shouldHaveNonStandardToString() {
    assertFalse(newInstance().toString().contains("@"));
  }

  public static TestDependencies newInstance() {
    return new TestDependencies(of(new LongNode(1L)));
  }
}