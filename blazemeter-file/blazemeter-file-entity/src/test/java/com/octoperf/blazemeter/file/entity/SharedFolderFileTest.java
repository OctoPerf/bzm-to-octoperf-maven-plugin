package com.octoperf.blazemeter.file.entity;

import com.google.common.testing.NullPointerTester;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
public class SharedFolderFileTest {
  @Test
  void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(SharedFolderFile.class).verify();
  }

  @Test
  void shouldPassNullPointerTester() {
    new NullPointerTester().testConstructors(SharedFolderFile.class, PACKAGE);
  }

  @Test
  void shouldCreate() {
    assertNotNull(newInstance());
  }

  @Test
  void shouldHaveNonStandardToString() {
    assertFalse(newInstance().toString().contains("@"));
  }

  public static SharedFolderFile newInstance() {
    return SharedFolderFile
      .builder()
      .name("name")
      .link("link")
      .build();
  }
}