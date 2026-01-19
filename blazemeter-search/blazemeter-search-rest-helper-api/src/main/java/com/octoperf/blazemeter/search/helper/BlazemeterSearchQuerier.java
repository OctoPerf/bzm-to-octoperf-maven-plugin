package com.octoperf.blazemeter.search.helper;

import com.octoperf.blazemeter.search.entity.SearchRequest;

import java.util.List;

@FunctionalInterface
public interface BlazemeterSearchQuerier<T> {

  List<T> search(SearchRequest request);
}
