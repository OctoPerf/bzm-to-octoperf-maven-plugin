package com.octoperf.blazemeter.test.entity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.quality.Strictness.LENIENT;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = LENIENT)
class TestDependenciesDeserializerTest {
  @Mock
  JsonParser jsonParser;
  @Mock
  ObjectCodec codec;
  @Mock
  JsonNode jsonNode;
  @Mock
  DeserializationContext context;

  TestDependenciesDeserializer deserializer;

  @BeforeEach
  void setup() throws IOException {
    deserializer = new TestDependenciesDeserializer();
    when(jsonParser.getCodec()).thenReturn(codec);
    when(codec.readTree(jsonParser)).thenReturn(jsonNode);
    when(codec.treeToValue(jsonNode, TestDependencies.class)).thenReturn(TestDependenciesTest.newInstance());
  }

  @Test
  void shouldDeserializeObject() throws IOException {
    when(jsonNode.isObject()).thenReturn(true);

    final Optional<TestDependencies> result = deserializer.deserialize(jsonParser, context);

    assertTrue(result.isPresent());
  }

  @Test
  void shouldDeserializeArray() throws IOException {
    when(jsonNode.isObject()).thenReturn(false);

    final Optional<TestDependencies> result = deserializer.deserialize(jsonParser, context);

    assertTrue(result.isEmpty());
  }
}