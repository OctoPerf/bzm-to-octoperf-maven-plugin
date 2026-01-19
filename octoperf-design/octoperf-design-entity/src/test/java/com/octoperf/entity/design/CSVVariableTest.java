package com.octoperf.entity.design;

import com.google.common.testing.NullPointerTester;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static com.octoperf.entity.design.VariableScope.PRIVATE;
import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CSVVariableTest {

  @Test
  void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(CSVVariable.class).verify();
  }

  @Test
  void shouldPassNullPointerTester() {
    new NullPointerTester().testConstructors(CSVVariable.class, PACKAGE);
  }

  @Test
  void shouldCreate() {
    assertNotNull(newInstance());
  }

  @Test
  void shouldHaveNonStandardToString() {
    assertFalse(newInstance().toString().contains("@"));
  }

  public static CSVVariable newInstance() {
    return CSVVariable
      .builder()
      .filename("filename")
      .encoding("")
      .delimiter(",")
      .allowQuotedData(true)
      .isRecycleOnEOF(false)
      .isStopThreadOnEOF(true)
      .scope(PRIVATE)
      .names(of("name"))
      .isShuffle(false)
      .ignoreFirstLine(true)
      .offset(1)
      .build();
  }
}