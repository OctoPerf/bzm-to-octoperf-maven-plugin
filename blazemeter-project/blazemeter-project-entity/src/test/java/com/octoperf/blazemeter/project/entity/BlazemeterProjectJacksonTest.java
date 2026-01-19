package com.octoperf.blazemeter.project.entity;

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
class BlazemeterProjectJacksonTest {
  @Autowired
  private JsonMapperService jsonService;

  @Test
  void shouldJacksonSerializeCorrectly() throws IOException {
    final BlazemeterProject dto = BlazemeterProjectTest.newInstance();

    final String json = jsonService.toJson(dto);
    final BlazemeterProject fromJson = jsonService.fromJson(json, BlazemeterProject.class);
    assertEquals(dto, fromJson);
  }
}