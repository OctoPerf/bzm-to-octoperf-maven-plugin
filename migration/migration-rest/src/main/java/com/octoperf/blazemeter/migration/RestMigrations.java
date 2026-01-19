package com.octoperf.blazemeter.migration;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.octoperf.blazemeter.migration.metadata.CopyMetadata;
import com.octoperf.blazemeter.migration.project.ProjectEntityMigrationService;
import com.octoperf.blazemeter.migration.sharedfile.SharedFileMigrationService;
import com.octoperf.blazemeter.migration.testdata.TestDataMigrationService;
import com.octoperf.blazemeter.migration.workspace.WorkspaceEntityMigrationService;
import com.octoperf.blazemeter.project.entity.BlazemeterProject;
import com.octoperf.blazemeter.test.entity.TestFile;
import com.octoperf.blazemeter.test.entity.TestListItem;
import com.octoperf.blazemeter.workspace.entity.BlazemeterWorkspace;
import com.octoperf.entity.design.JmxImportResult;
import com.octoperf.entity.design.Project;
import com.octoperf.maven.api.*;
import com.octoperf.workspace.entity.Workspace;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Predicates.alwaysTrue;
import static com.octoperf.blazemeter.migration.metadata.CopyMetadata.merge;
import static java.lang.String.format;
import static java.util.Map.of;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.BooleanUtils.isFalse;


@Slf4j
@Service
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class RestMigrations implements BlazemeterToOctoperfMigrationService {
  static final CopyMetadata EMPTY_COPY = CopyMetadata.builder()
    .workspaces(of())
    .projects(of())
    .projectIdToBzmFolderIds(ImmutableMultimap.of())
    .projectIdToBzmTestIds(ImmutableMultimap.of())
    .bzmTestIdToBzmWorkspaceId(of())
    .build();

  @NonNull
  final WorkspaceEntityMigrationService workspaces;
  @NonNull
  final ProjectEntityMigrationService projects;
  @NonNull
  final OctoperfProjectFiles projectFiles;
  @NonNull
  final OctoperfVirtualUsers vus;
  @NonNull
  final BlazemeterWorkspaces bzmW;
  @NonNull
  final BlazemeterProjects bzmP;
  @NonNull
  final BlazemeterTests bzmT;
  @NonNull
  final TestDataMigrationService testDataMigrations;
  @NonNull
  final SharedFileMigrationService sharedFileMigrations;

  @Override
  public void migrate(final BlazemeterToOctoperfMigrationRequest r) {
    if (r.getBzmProjectId().isPresent()) {
      checkArgument(r.getBzmWorkspaceId().isPresent(), "Workspace Id is required for project migration");
    }
    log.info("------ [BlazeMeter to OctoPerf] starting migration ------");

    final CopyMetadata copyMetadata = bzmW.getWorkspaces()
      .stream()
      .filter(buildWorkspaceFilter(r.getBzmWorkspaceId()))
      .map(w -> copyBzmWorkspace(w, r))
      .reduce(CopyMetadata::merge)
      .orElse(EMPTY_COPY);

    sharedFileMigrations.migrateSharedFiles(copyMetadata);

    testDataMigrations.migrateTestData(copyMetadata);

    log.info("------ [BlazeMeter to OctoPerf] migration done ------");

    log.info("Metadata of migration: {}", copyMetadata);
  }



  CopyMetadata copyBzmWorkspace(final BlazemeterWorkspace bzmW, final BlazemeterToOctoperfMigrationRequest r) {

    final Workspace w = workspaces.migrateWorkspaceEntity(bzmW.getName(), r.isAlwaysCreateNewWorkspace());
    log.info("[WORKSPACE] '{}'", w.getName());

    final CopyMetadata projectsCopy = bzmP.getProjects()
      .stream()
      .filter(buildProjectFilter(r.getBzmProjectId()))
      .map(p -> copyBzmProject(w, p, r))
      .reduce(CopyMetadata::merge)
      .orElse(EMPTY_COPY);

    return merge(
      EMPTY_COPY.withWorkspaces(ImmutableMap.of(w.getId(), w)),
      projectsCopy
    );
  }

  CopyMetadata copyBzmProject(final Workspace w,
                              final BlazemeterProject bzmP,
                              final BlazemeterToOctoperfMigrationRequest r) {

    final Project p = projects.migrateProjectEntity(w.getId(), bzmP.getName(), r.isAlwaysCreateNewProject());
    log.info("[PROJECT] '{}'", p.getName());

    final CopyMetadata testsCopy = bzmT.getTests(bzmP.getId())
      .stream()
      .filter(t -> "jmeter".equals(t.getConfiguration().getScriptType()))
      .filter(t -> isFalse(t.getConfiguration().getFilename().isEmpty()))
      .map(t -> copyBzmTest(p.getId(), bzmP.getWorkspaceId(), t))
      .reduce(CopyMetadata::merge)
      .orElse(EMPTY_COPY);

    return merge(
      EMPTY_COPY.withProjects(ImmutableMap.of(p.getId(), p)),
      testsCopy
    );
  }

  CopyMetadata copyBzmTest(final String projectId, final int bzmWorkspaceId, final TestListItem bzmTest) {
    final String jmxFilename = bzmTest.getConfiguration().getFilename();

    final Map<String, TestFile> bzmFiles = bzmT.getTestFiles(bzmTest.getId())
      .stream()
      .collect(toMap(TestFile::getName, f -> f));

    final TestFile jmxFile = requireNonNull(bzmFiles.get(jmxFilename), format("Unable to find BZM jmx file %s", jmxFilename));
    log.info("[IMPORT JMX] BZM script file = '{}':", jmxFile.getName());

    Try.run(() -> {
      final JmxImportResult jmxResult = vus.importJmx(projectId, jmxFilename, jmxFile.getLink());
      jmxResult.getVirtualUsers().forEach(vu -> log.info("[IMPORT JMX - VU] '{}' imported", vu.getName()));
    });

    bzmFiles
      .values()
      .stream()
      .filter(f -> isFalse(f.getName().equals(jmxFilename)))
      .forEach(f -> Try.run(() -> {
        projectFiles.upload(projectId, f.getName(), f.getLink());
        log.info("[PROJECT FILES] '{}'", f.getName());
      }));

    return EMPTY_COPY
      .withProjectIdToBzmFolderIds(
        ImmutableMultimap.<String, String>builder()
          .putAll(projectId, bzmTest.getConfiguration().getPlugins().getSharedFolders())
          .build()
      )
      .withProjectIdToBzmTestIds(ImmutableMultimap.of(projectId, bzmTest.getId()))
      .withBzmTestIdToBzmWorkspaceId(of(bzmTest.getId(), bzmWorkspaceId));
  }

  static Predicate<BlazemeterWorkspace> buildWorkspaceFilter(final Optional<Integer> bzmWorkspaceId) {
    return bzmWorkspaceId
      .map(wId -> (Predicate<BlazemeterWorkspace>) w -> w.getId() == wId)
      .orElse(alwaysTrue());
  }

  static Predicate<BlazemeterProject> buildProjectFilter(final Optional<Integer> bzmProjectId) {
    return bzmProjectId
      .map(pId -> (Predicate<BlazemeterProject>) p -> p.getId() == pId)
      .orElse(alwaysTrue());
  }
}
