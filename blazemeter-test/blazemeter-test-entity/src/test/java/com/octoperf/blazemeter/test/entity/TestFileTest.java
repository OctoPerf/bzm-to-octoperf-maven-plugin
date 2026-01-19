package com.octoperf.blazemeter.test.entity;

import com.google.common.testing.NullPointerTester;
import com.octoperf.blazemeter.test.entity.TestFile.TestFileBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestFileTest {

  @Test
  void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(TestFile.class).verify();
  }

  @Test
  void shouldPassNullPointerTester() {
    new NullPointerTester().testConstructors(TestFile.class, PACKAGE);
  }

  @Test
  void shouldCreate() {
    assertNotNull(newInstance());
  }

  @Test
  void shouldHaveNonStandardToString() {
    assertFalse(newInstance().toString().contains("@"));
  }

  public static TestFile newInstance() {
    return newBuilder().build();
  }

  public static TestFileBuilder newBuilder() {
    return TestFile
      .builder()
      .name("name")
      .link("link");
  }
}