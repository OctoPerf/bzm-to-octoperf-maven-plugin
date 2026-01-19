package com.octoperf.blazemeter.search.entity;

import com.google.common.testing.NullPointerTester;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SearchListResultTest {
  @Test
  void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(SearchListResult.class).verify();
  }

  @Test
  void shouldPassNullPointerTester() {
    new NullPointerTester().testConstructors(SearchListResult.class, PACKAGE);
  }

  @Test
  void shouldCreate() {
    assertNotNull(newInstance());
  }

  @Test
  void shouldHaveNonStandardToString() {
    assertFalse(newInstance().toString().contains("@"));
  }

  public static SearchListResult<Integer> newInstance() {
    return new SearchListResult<>(of(1, 2));
  }
}