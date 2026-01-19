package com.octoperf.blazemeter.search.helper;

import com.google.common.collect.ImmutableList;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.collect.ImmutableList.builder;
import static lombok.AccessLevel.PRIVATE;

@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
class BlazemeterSearchRestHelpers implements BlazemeterSearchRestHelper {
  static final int LIMIT = 100;

  @Override
  public <T> List<T> searchAll(final BlazemeterQuerier<T> querier) {
    final ImmutableList.Builder<T> builder = builder();

    int skip = 0;
    boolean hasMore = true;
    while (hasMore) {
      final List<T> result = querier.search(skip, limit());
      builder.addAll(result);
      skip += result.size();
      hasMore = limit() == result.size();
    }

    return builder.build();
  }

  @Override
  public int limit() {
    return LIMIT;
  }
}
