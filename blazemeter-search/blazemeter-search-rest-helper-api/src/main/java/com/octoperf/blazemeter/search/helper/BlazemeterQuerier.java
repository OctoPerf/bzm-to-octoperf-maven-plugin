package com.octoperf.blazemeter.search.helper;

import java.util.List;

@FunctionalInterface
public interface BlazemeterQuerier<T> {

  List<T> search(int skip, int limit);
}
