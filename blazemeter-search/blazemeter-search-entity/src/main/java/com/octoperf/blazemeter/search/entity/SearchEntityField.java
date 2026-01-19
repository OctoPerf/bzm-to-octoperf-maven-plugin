package com.octoperf.blazemeter.search.entity;

import static java.util.Objects.requireNonNull;

public enum SearchEntityField {
  WORKSPACE("workspace"),
  PROJECT("project"),
  TEST("test-union");

  private final String fieldName;

  SearchEntityField(final String fieldName) {
    this.fieldName = requireNonNull(fieldName);
  }

  String fieldName() {
    return fieldName;
  }

  public static SearchRequest newSearchRequest(final SearchEntityField field, final int skip, final int limit) {
    return new SearchRequest(field.fieldName, skip, limit);
  }
}
