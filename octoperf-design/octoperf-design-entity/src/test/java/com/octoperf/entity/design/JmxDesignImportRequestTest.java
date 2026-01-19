package com.octoperf.entity.design;

import com.google.common.testing.NullPointerTester;
import com.octoperf.entity.design.JmxDesignImportRequest.JmxDesignImportRequestBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static com.octoperf.entity.design.AdBlocking.DISABLED;
import static com.octoperf.entity.design.HtmlResources.KEEP_ALL;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JmxDesignImportRequestTest {

  @Test
  void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(JmxDesignImportRequest.class).verify();
  }

  @Test
  void shouldPassNullPointerTester() {
    new NullPointerTester().testConstructors(JmxDesignImportRequest.class, PACKAGE);
  }
  @Test
  void shouldCreate() {
    assertNotNull(newInstance());
  }

  @Test
  void shouldHaveNonStandardToString() {
    assertFalse(newInstance().toString().contains("@"));
  }

  public static JmxDesignImportRequest newInstance() {
    return builder().build();
  }

  public static JmxDesignImportRequestBuilder builder() {
    return JmxDesignImportRequest
      .builder()
      .resources(KEEP_ALL)
      .adBlocking(DISABLED);
  }
}