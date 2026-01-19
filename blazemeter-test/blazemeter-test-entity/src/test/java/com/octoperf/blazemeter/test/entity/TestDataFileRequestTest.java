package com.octoperf.blazemeter.test.entity;

import com.fasterxml.jackson.databind.node.LongNode;
import com.google.common.testing.NullPointerTester;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestDataFileRequestTest {
  @Test
  void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(TestDataFileRequest.class).verify();
  }

  @Test
  void shouldPassNullPointerTester() {
    new NullPointerTester().testConstructors(TestDataFileRequest.class, PACKAGE);
  }

  @Test
  void shouldCreate() {
    assertNotNull(newInstance());
  }

  @Test
  void shouldHaveNonStandardToString() {
    assertFalse(newInstance().toString().contains("@"));
  }

  public static TestDataFileRequest newInstance() {
    return new TestDataFileRequest(
      new TestDataFileRequest.Data(
        "type",
        new TestDataFileRequest.Attributes(new LongNode(1L))
      )
    );
  }
}