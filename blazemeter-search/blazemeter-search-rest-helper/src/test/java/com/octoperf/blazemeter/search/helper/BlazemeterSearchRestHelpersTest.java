package com.octoperf.blazemeter.search.helper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.quality.Strictness.LENIENT;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = LENIENT)
class BlazemeterSearchRestHelpersTest {

  @Mock
  private BlazemeterQuerier<Integer> querier;

  @BeforeEach
  void setup() {
    when(querier.search(0, 1)).thenReturn(List.of(2));
    when(querier.search(1, 1)).thenReturn(List.of(3));
  }

  @Test
  void shouldSearchAll() {
    assertEquals(100, BlazemeterSearchRestHelpers.LIMIT);

    final BlazemeterSearchRestHelpers helper = new BlazemeterSearchRestHelpers();
    assertEquals(100, helper.limit());

    final BlazemeterSearchRestHelpers helperSpy = spy(helper);
    doReturn(1).when(helperSpy).limit();

    assertEquals(List.of(2, 3), helperSpy.searchAll(querier));
  }
}