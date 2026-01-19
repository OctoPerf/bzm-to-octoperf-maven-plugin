package com.octoperf.entity.design;

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
class JmxDesignImportRequestJacksonTest {
  @Autowired
  private JsonMapperService jsonService;

  @Test
  void shouldJacksonSerializeCorrectly() throws IOException {
    final JmxDesignImportRequest dto = JmxDesignImportRequestTest.newInstance();

    final String json = jsonService.toJson(dto);
    final JmxDesignImportRequest fromJson = jsonService.fromJson(json, JmxDesignImportRequest.class);
    assertEquals(dto, fromJson);
  }
}