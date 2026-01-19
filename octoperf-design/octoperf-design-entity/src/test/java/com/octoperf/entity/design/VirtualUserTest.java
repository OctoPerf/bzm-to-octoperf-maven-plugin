package com.octoperf.entity.design;

import com.google.common.testing.NullPointerTester;
import com.octoperf.entity.design.VirtualUser.VirtualUserBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests {@link VirtualUser}.
 *
 * @author jerome
 */
public class VirtualUserTest {

  @Test
  void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(VirtualUser.class).verify();
  }

  @Test
  void shouldPassNullPointerTester() {
    new NullPointerTester().testConstructors(VirtualUser.class, PACKAGE);
  }
  @Test
  void shouldCreate() {
    assertNotNull(newInstance());
  }

  @Test
  void shouldHaveNonStandardToString() {
    assertFalse(newInstance().toString().contains("@"));
  }

  public static VirtualUser newInstance() {
    return builder().build();
  }

  public static VirtualUserBuilder builder() {
    return VirtualUser
      .builder()
      .projectId("projectId")
      .id("virtualUserId")
      .name("name")
      .description("description");
  }
}
