package com.octoperf.tools.retrofit.security.blazemeter;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import retrofit2.Retrofit;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(makeFinal=true, level= PRIVATE)
final class StdRetrofitApiWrapper implements BlazemeterStdApiWrapper {
  @NonNull
  Retrofit retrofit;

  @Override
  public <T> T create(final Class<T> service) {
    return retrofit.create(service);
  }
}
