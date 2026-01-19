package com.octoperf.tools.retrofit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RetrofitCallServiceTest {

  @Mock
  Call<Object> call;

  private Response<Object> response;
  private CallService service;

  @BeforeEach
  void before() {
    service = new RetrofitCallService();
  }

  @Test
  void shouldExecuteFineWithNull() throws IOException {
    response = Response.success(null);
    when(call.execute()).thenReturn(response);
    final Optional<Object> execute = service.execute(call);
    assertFalse(execute.isPresent());
  }

  @Test
  void shouldExecuteFine() throws IOException {
    response = Response.success(new Object());
    when(call.execute()).thenReturn(response);
    final Optional<Object> execute = service.execute(call);
    assertTrue(execute.isPresent());
  }

  @Test
  void shouldExecuteWithError() throws IOException {
    when(call.execute()).thenThrow(new IOException());
    final Optional<Object> execute = service.execute(call);
    assertFalse(execute.isPresent());
  }
}
