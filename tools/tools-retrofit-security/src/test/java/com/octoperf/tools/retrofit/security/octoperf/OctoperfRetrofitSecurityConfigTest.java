package com.octoperf.tools.retrofit.security.octoperf;

import com.octoperf.Application;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
class OctoperfRetrofitSecurityConfigTest {

  @Autowired
  OctoperfApiWrapperFactory factory;

  @Test
  void shouldCheck() {
    assertNotNull(factory);
  }
}
