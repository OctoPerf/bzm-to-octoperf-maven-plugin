package com.octoperf.blazemeter.test.entity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

/**
 * BlazeMeter API returns different JSON field types for the same field.
 * <p>
 * It is either  "dependencies": [] OR "dependencies": { "data": { ... } }
 */
public class TestDependenciesDeserializer extends JsonDeserializer<Optional<TestDependencies>> {

  @Override
  public Optional<TestDependencies> deserialize(final JsonParser p, final DeserializationContext context) throws IOException {

    final JsonNode node = p.getCodec().readTree(p);

    if (node.isObject()) {
      return ofNullable(p.getCodec().treeToValue(node, TestDependencies.class));
    }

    return empty();
  }
}
