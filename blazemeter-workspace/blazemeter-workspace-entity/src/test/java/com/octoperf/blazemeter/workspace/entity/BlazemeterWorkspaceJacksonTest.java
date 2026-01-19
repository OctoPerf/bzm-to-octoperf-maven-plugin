package com.octoperf.blazemeter.workspace.entity;

import com.octoperf.Application;
import com.octoperf.tools.jackson.mapper.JsonMapperService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes= Application.class)
class BlazemeterWorkspaceJacksonTest {

  @Autowired
  private JsonMapperService jsonService;
  
  @Test
  void shouldJacksonSerializeCorrectly() throws IOException {
    final BlazemeterWorkspace dto = BlazemeterWorkspaceTest.newInstance();
    
    final String json = jsonService.toJson(dto);
    final BlazemeterWorkspace fromJson = jsonService.fromJson(json, BlazemeterWorkspace.class);
    assertEquals(dto, fromJson);
  }
}
