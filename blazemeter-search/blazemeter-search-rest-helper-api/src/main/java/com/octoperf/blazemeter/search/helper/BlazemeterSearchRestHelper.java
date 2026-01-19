package com.octoperf.blazemeter.search.helper;

import java.util.List;

public interface BlazemeterSearchRestHelper {

  <T> List<T> searchAll(BlazemeterQuerier<T> querier);

  int limit();
}
