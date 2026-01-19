package com.octoperf.blazemeter.test.entity;

import com.octoperf.Application;
import com.octoperf.tools.jackson.mapper.JsonMapperService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
class TestDependenciesJacksonTest {
  @Autowired
  private JsonMapperService jsonService;

  @Test
  void shouldJacksonSerializeCorrectly() throws IOException {
    final TestDependencies dto = TestDependenciesTest.newInstance();

    final String json = jsonService.toJson(dto);
    final TestDependencies fromJson = jsonService.fromJson(json, TestDependencies.class);
    assertEquals(dto.toString(), fromJson.toString());
  }
}