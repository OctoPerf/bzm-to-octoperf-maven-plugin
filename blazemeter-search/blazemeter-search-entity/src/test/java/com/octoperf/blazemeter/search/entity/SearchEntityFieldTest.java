package com.octoperf.blazemeter.search.entity;

import org.junit.jupiter.api.Test;

import static com.octoperf.blazemeter.search.entity.SearchEntityField.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchEntityFieldTest {

  @Test
  void shouldHaveCorrectName() {
    assertEquals("workspace", WORKSPACE.fieldName());
    assertEquals("project", PROJECT.fieldName());
    assertEquals("test-union", TEST.fieldName());
  }

  @Test
  void shouldNewSearchRequest() {
    assertEquals(new SearchRequest(WORKSPACE.fieldName(), 1, 2), newSearchRequest(WORKSPACE, 1, 2));
  }
}