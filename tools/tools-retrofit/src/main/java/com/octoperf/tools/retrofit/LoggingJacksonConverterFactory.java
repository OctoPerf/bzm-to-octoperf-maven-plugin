package com.octoperf.tools.retrofit;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.jspecify.annotations.NonNull;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class LoggingJacksonConverterFactory extends Converter.Factory implements LoggingConverterFactory {

  Converter.Factory delegate;

  public LoggingJacksonConverterFactory(final ObjectMapper mapper) {
    super();
    this.delegate = JacksonConverterFactory.create(mapper);
  }

  @Override
  public Converter.Factory newConverterFactory() {
    return this;
  }

  @Override
  public Converter<?, RequestBody> requestBodyConverter(
    @NonNull final Type type,
    final Annotation @NonNull [] parameterAnnotations,
    final Annotation @NonNull [] methodAnnotations,
    @NonNull final Retrofit retrofit) {

    return delegate.requestBodyConverter(
      type, parameterAnnotations, methodAnnotations, retrofit);
  }

  @Override
  public Converter<ResponseBody, ?> responseBodyConverter(@NonNull final Type type,
                                                          final Annotation @NonNull [] annotations,
                                                          @NonNull final Retrofit retrofit) {

    final Converter<ResponseBody, ?> converter = requireNonNull(
      delegate.responseBodyConverter(type, annotations, retrofit)
    );
    return (ResponseBody body) -> {
      final String rawBody = body.string();
      try {
        return converter.convert(ResponseBody.create(rawBody, body.contentType()));
      } catch (final IOException e) {
        log.error("DESERIALIZATION ERROR\nRaw HTTP response:\n{}", rawBody, e);
        throw e;
      }
    };
  }
}
