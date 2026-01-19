package com.octoperf.blazemeter.file.entity;

import com.google.common.testing.NullPointerTester;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SharedFolderListResultTest {

  @Test
  void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(SharedFolderListResult.class).verify();
  }

  @Test
  void shouldPassNullPointerTester() {
    new NullPointerTester().testConstructors(SharedFolderListResult.class, PACKAGE);
  }

  @Test
  void shouldCreate() {
    assertNotNull(newInstance());
  }

  @Test
  void shouldHaveNonStandardToString() {
    assertFalse(newInstance().toString().contains("@"));
  }

  public static SharedFolderListResult newInstance() {
    return new SharedFolderListResult(SharedFolderFilesTest.newInstance());
  }
}