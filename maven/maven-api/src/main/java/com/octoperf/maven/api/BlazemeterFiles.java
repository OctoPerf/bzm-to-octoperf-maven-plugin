package com.octoperf.maven.api;

import com.octoperf.blazemeter.file.entity.SharedFolderFile;

import java.util.List;

public interface BlazemeterFiles {

  List<SharedFolderFile> getSharedFiles(String sharedFolderId);
}
