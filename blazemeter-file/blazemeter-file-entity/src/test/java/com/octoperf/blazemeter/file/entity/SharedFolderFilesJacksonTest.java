package com.octoperf.blazemeter.file.entity;

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
class SharedFolderFilesJacksonTest {
  @Autowired
  private JsonMapperService jsonService;

  @Test
  void shouldJacksonSerializeCorrectly() throws IOException {
    final SharedFolderFiles dto = SharedFolderFilesTest.newInstance();

    final String json = jsonService.toJson(dto);
    final SharedFolderFiles fromJson = jsonService.fromJson(json, SharedFolderFiles.class);
    assertEquals(dto, fromJson);
  }
}