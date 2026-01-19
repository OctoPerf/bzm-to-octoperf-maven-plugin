package com.octoperf.entity.design;

import com.google.common.testing.NullPointerTester;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VariableWrapperTest {

  @Test
  void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(VariableWrapper.class).verify();
  }

  @Test
  void shouldPassNullPointerTester() {
    new NullPointerTester()
      .setDefault(CSVVariable.class, CSVVariableTest.newInstance())
      .testConstructors(VariableWrapper.class, PACKAGE);
  }

  @Test
  void shouldCreate() {
    assertNotNull(newInstance());
  }

  @Test
  void shouldHaveNonStandardToString() {
    assertFalse(newInstance().toString().contains("@"));
  }

  public static VariableWrapper newInstance() {
    return VariableWrapper
      .builder()
      .id("id")
      .userId("userId")
      .projectId("projectId")
      .name("name")
      .description("description")
      .variable(CSVVariableTest.newInstance())
      .build();
  }
}