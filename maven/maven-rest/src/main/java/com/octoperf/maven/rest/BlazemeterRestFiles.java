package com.octoperf.maven.rest;

import com.octoperf.blazemeter.file.entity.SharedFolderFile;
import com.octoperf.blazemeter.file.entity.SharedFolderFiles;
import com.octoperf.blazemeter.file.entity.SharedFolderListResult;
import com.octoperf.blazemeter.file.rest.api.BlazemeterFilesApi;
import com.octoperf.maven.api.BlazemeterFiles;
import com.octoperf.tools.retrofit.CallService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.List.of;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class BlazemeterRestFiles implements BlazemeterFiles {
  @NonNull
  BlazemeterFilesApi api;
  @NonNull
  CallService calls;

  @Override
  public List<SharedFolderFile> getSharedFiles(final String sharedFolderId) {
    return calls.execute(api.getFiles(sharedFolderId))
      .map(SharedFolderListResult::getResult)
      .map(SharedFolderFiles::getFiles)
      .orElse(of());
  }
}
