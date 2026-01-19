package com.octoperf.entity.design;

import com.google.common.testing.NullPointerTester;
import com.octoperf.entity.design.JmxImportResult.JmxImportResultBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JmxImportResultTest {

  @Test
  void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(JmxImportResult.class).verify();
  }

  @Test
  void shouldPassNullPointerTester() {
    new NullPointerTester().testConstructors(JmxImportResult.class, PACKAGE);
  }

  @Test
  void shouldCreate() {
    assertNotNull(newInstance());
  }

  @Test
  void shouldHaveNonStandardToString() {
    assertFalse(newInstance().toString().contains("@"));
  }

  public static JmxImportResult newInstance() {
    return builder().build();
  }

  public static JmxImportResultBuilder builder() {
    return JmxImportResult
      .builder()
      .virtualUsers(of(VirtualUserTest.newInstance()));
  }
}