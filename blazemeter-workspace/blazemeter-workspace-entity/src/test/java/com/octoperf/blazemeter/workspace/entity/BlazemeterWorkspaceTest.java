package com.octoperf.blazemeter.workspace.entity;

import com.google.common.testing.NullPointerTester;
import com.octoperf.blazemeter.workspace.entity.BlazemeterWorkspace.BlazemeterWorkspaceBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BlazemeterWorkspaceTest {

  @Test
  void shouldPassNPE() {
    final BlazemeterWorkspace entity = create();
    new NullPointerTester()
      .setDefault(Optional.class, Optional.empty())
      .testConstructors(entity.getClass(), PACKAGE);
  }

  @Test
  void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(BlazemeterWorkspace.class).verify();
  }

  @Test
  void shouldPassNullPointerTester() {
    new NullPointerTester().testConstructors(BlazemeterWorkspace.class, PACKAGE);
  }

  @Test
  void shouldCreate() {
    assertNotNull(create());
  }

  @Test
  void shouldHaveNonStandardToString() {
    assertFalse(create().toString().contains("@"));
  }

  protected BlazemeterWorkspace create() {
    return newInstance();
  }

  public static BlazemeterWorkspace newInstance() {
    return newBuilder().build();
  }

  public static BlazemeterWorkspaceBuilder newBuilder() {
    return BlazemeterWorkspace
      .builder()
      .id(1)
      .name("");
  }
}
