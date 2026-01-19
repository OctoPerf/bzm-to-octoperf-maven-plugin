package com.octoperf.entity.design;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Builder;
import lombok.Value;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static java.util.Objects.requireNonNull;


@Value
@Builder
@JsonTypeInfo(use = NAME, include = PROPERTY)
public class CSVVariable {
  String filename;
  String encoding;
  String delimiter;
  boolean allowQuotedData;
  boolean isRecycleOnEOF;
  boolean isStopThreadOnEOF;
  VariableScope scope;
  List<String> names;
  boolean isShuffle;
  boolean ignoreFirstLine;
  int offset;

  @JsonCreator
  CSVVariable(@JsonProperty("filename") final String filename,
              @JsonProperty("encoding") final String encoding,
              @JsonProperty("delimiter") final String delimiter,
              @JsonProperty("allowQuotedData") final boolean allowQuotedData,
              @JsonProperty("recycleOnEOF") final boolean isRecycleOnEOF,
              @JsonProperty("stopThreadOnEOF") final boolean isStopThreadOnEOF,
              @JsonProperty("scope") final VariableScope scope,
              @JsonProperty("names") final List<String> names,
              @JsonProperty("shuffle") final boolean isShuffle,
              @JsonProperty("ignoreFirstLine") final boolean ignoreFirstLine,
              @JsonProperty("offset") final int offset) {
    super();
    this.filename = requireNonNull(filename);
    this.encoding = requireNonNull(encoding);
    this.delimiter = requireNonNull(delimiter);
    this.allowQuotedData = allowQuotedData;
    this.isRecycleOnEOF = isRecycleOnEOF;
    this.isStopThreadOnEOF = isStopThreadOnEOF;
    this.scope = requireNonNull(scope);
    this.names = requireNonNull(names);
    this.isShuffle = isShuffle;
    this.ignoreFirstLine = ignoreFirstLine;
    this.offset = Math.abs(offset);
  }
}
