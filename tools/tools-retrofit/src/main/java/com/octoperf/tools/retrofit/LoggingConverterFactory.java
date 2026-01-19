package com.octoperf.tools.retrofit;

import retrofit2.Converter;

public interface LoggingConverterFactory {

  Converter.Factory newConverterFactory();
}
