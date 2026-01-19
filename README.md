# BlazeMeter to OctoPerf Maven Plugin

The **BlazeMeter to OctoPerf Maven Plugin** allows you to migrate your BlazeMeter **test design assets** into OctoPerf.

It converts supported BlazeMeter entities into their OctoPerf equivalents, enabling a smooth transition of your test designs.

This tool uses BlazeMeter publicly documented APIs only and does not scrape the user interface, bypass access controls, or reverse engineer BlazeMeter.

## 🚀 Quick Start (Beginner-friendly)

If you are not familiar with Maven, you can use the ready-to-run example project provided in this repository.

### Steps

1. Install Maven  
   https://maven.apache.org/install.html

2. Verify installation:
    ```bash
    mvn --version
    ```

3. Download the example project:

   - Go to: [example/](example/)
   - Download the `pom.xml` file and place it in an empty directory on your computer.
   
   > **Tip:** You can download it directly here: [pom.xml](https://github.com/OctoPerf/bzm-to-octoperf-maven-plugin/blob/main/example/pom.xml)

4. Edit pom.xml and fill in your API keys:

    ```
    <octoPerfApiKey>YOUR_OCTOPERF_API_KEY</octoPerfApiKey>
    <blazemeterApiKeyId>YOUR_BZM_API_KEY_ID</blazemeterApiKeyId>
    <blazemeterApiKeySecret>YOUR_BZM_API_KEY_SECRET</blazemeterApiKeySecret>   
    ```

5. Run the migration from the directory containing the pom.xml file:

    ```bash
    mvn com.octoperf:bzm-to-octoperf-maven-plugin:1.0.0:migrate
    ```

That’s it 🎉
The plugin will migrate your BlazeMeter assets into OctoPerf.

## What is imported?

The plugin migrates the following elements:

- **Workspaces and Projects**

BlazeMeter Workspaces and Projects are converted into their OctoPerf equivalents.

- **Test Designs (Virtual Users)**
  
The design part of BlazeMeter tests is imported as Virtual Users in OctoPerf.

⚠️ **Only JMeter-based BlazeMeter tests are supported.** Other test types (e.g. Locust, Gatling) are ignored.

- **Test Files**

BlazeMeter test files are imported into the OctoPerf **Project Files** section.

All files directly attached to your tests (except the JMX script itself, which is imported as a Virtual User) are uploaded.

- **Shared Folders**

BlazeMeter workspace-level shared folders are imported as Project files in OctoPerf.

Only files that are actually referenced by tests (Virtual Users) are migrated.

> **Note:** BlazeMeter manages shared folders at the workspace level, whereas OctoPerf only supports files at the project level (no shared folder concept).

- **Test Data Sets (CSV Variables)**

BlazeMeter test data sets are converted into CSV variables in OctoPerf, along with their corresponding CSV files.

> **Note:** In OctoPerf, variables are defined at the **project level**.
> If multiple imported tests use the same variable name, naming conflicts may occur.
> It is recommended to ensure that variable names are unique across your project, either before or after migration.

You can rename variables in OctoPerf after migration. Any conflicts will be reported during the Virtual User sanity check after migration.

## Migration scope

Refer to the plugin parameters section below for full details. In summary, you can perform one of the following:
- A **full migration** of all supported customer-configured assets accessible via the public BlazeMeter API
- A **single Workspace migration** (by providing a BlazeMeter workspace ID)
- A **single Project migration** (by providing both a BlazeMeter workspace ID and a BlazeMeter project ID)

## Workspace and Project reuse

In OctoPerf, only administrators can delete Workspaces.

If you need more control over Workspace creation during migration, the plugin allows you to:
- Reuse an existing Workspace with the same name instead of creating a new one
- Reuse an existing Project with the same name instead of creating a new one

This helps prevent unnecessary duplication of Workspaces and Projects during repeated migrations.

## Distribution

The plugin is distributed via the [OctoPerf Maven Repository](https://github.com/OctoPerf/maven-repository) hosted on GitHub.

**Latest released version**: `1.0.0`

## Compatibility

| Plugin Version | OctoPerf Version |
|----------------|------------------|
| `1.0.0+`       | `16.1.0+`        |

## Goals Overview

The OctoPerf plugin has the following goals:

- `com.octoperf:bzm-to-octoperf-maven-plugin:${version}:migrate`: Migrates BlazeMeter data to OctoPerf.

## System Requirements

The following specifies the minimum requirements to run this Maven plugin:

| Name       | Description                      |
|------------|----------------------------------|
| Maven      | 3.6                              |
| Java       | 17                               |
| Memory     | No specific minimum requirement  |
| Disk Space | No specific minimum requirement  |

## Migration parameters 

### Mandatory parameters

| Name                      | Maven Parameter name    | Description                                                                                                                                         |
|---------------------------|-------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------|
| OctoPerf API Key          | octoPerfApiKey          | Log in to OctoPerf [Account > Profile](https://api.octoperf.com/doc/account/profile/#apikey) and copy the API key                                   |
| BlazeMeter API Key Id     | blazemeterApiKeyId      | Log in to BlazeMeter [Settings > Personal > API Keys](https://help.blazemeter.com/docs/guide/api-blazemeter-api-keys.html), and copy the API key Id |
| BlazeMeter API Key Secret | blazemeterApiKeySecret  | Log in to BlazeMeter [Settings > Personal > API Keys](https://help.blazemeter.com/docs/guide/api-blazemeter-api-keys.html), and copy the API key Secret                                                                |

> ⚠️ **Security note:** Do not commit API keys to source control. Use environment variables or Maven profiles for sensitive values.

### Optional parameters

| Name                        | Maven Parameter name     | Description                                                                            |
|-----------------------------|--------------------------|----------------------------------------------------------------------------------------|
| OctoPerf Server Url         | octoPerfServerUrl        | OctoPerf API server base URL (default to SaaS platform)                                |
| Always create new Workspace | alwaysCreateNewWorkspace | Create a new OctoPerf Workspace even if one with the same name exists (default: false) |
| Always create new Project   | alwaysCreateNewProject   | Create a new OctoPerf Project even if one with the same name exists (default: false)   |
| BlazeMeter Workspace Id     | bzmWorkspaceId           | BlazeMeter Workspace id to migrate a single Workspace                                  |
| BlazeMeter Project Id       | bzmProjectId             | BlazeMeter Project id to migrate a single Project (bzmWorkspaceId is also required)    |

## Usage

Specify the plugin version in your project's plugin configuration:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <packaging>pom</packaging>
  <groupId>com.octoperf</groupId>
  <artifactId>octoperf-test</artifactId>
  <version>1.0.0-SNAPSHOT</version>

  <properties>
    <maven.compiler.source>17</maven.compiler.source>
    <maven.compiler.target>17</maven.compiler.target>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  </properties>
  
  <build>
    <plugins>
      <plugin>
        <groupId>com.octoperf</groupId>
        <artifactId>bzm-to-octoperf-maven-plugin</artifactId>
        <version>1.0.0</version>
        <configuration>
          <!-- See configuration below -->
        </configuration>
      </plugin>
    </plugins>
  </build>

  <!-- OctoPerf Maven Repository -->
  <pluginRepositories>
  	<pluginRepository>
      <id>octoperf-snapshots</id>
      <name>OctoPerf Snapshots</name>
      <url>https://github.com/octoperf/maven-repository/raw/master/snapshots</url>
    </pluginRepository>
    <pluginRepository>
      <id>octoperf-releases</id>
      <name>OctoPerf Releases</name>
      <url>https://github.com/octoperf/maven-repository/raw/master/releases</url>
    </pluginRepository>
  </pluginRepositories>
</project>
```

### Migration configuration

```xml
<configuration>

  <!--                                  -->
  <!-- All Parameters for the migration -->
  <!--                                  -->

  <!-- Required parameters -->
  <!--                     -->

  <!-- Your API key from your User Account -->
  <octoPerfApiKey>OCTOPERF_API_KEY</octoPerfApiKey>

  <!-- Your BlazeMeter API Keys from Settings/Personal/API Keys -->
  <blazemeterApiKeyId>BZM_API_KEY_ID</blazemeterApiKeyId>
  <blazemeterApiKeySecret>BZM_API_KEY_SECRET</blazemeterApiKeySecret>

  <!-- Optional parameters -->
  <!--                     -->

  <!-- Default to SaaS platform -->
  <octoPerfServerUrl>https://api.octoperf.com</octoPerfServerUrl>

  <!-- Create a new OctoPerf Workspace even if one with the same name exists (default: false)  -->
  <alwaysCreateNewWorkspace>false</alwaysCreateNewWorkspace>

  <!-- Create a new OctoPerf Project even if one with the same name exists (default: false)  -->
  <alwaysCreateNewProject>false</alwaysCreateNewProject>

  <!-- BlazeMeter Workspace id to migrate a single Workspace 
       (also required when you want to migrate a single project)
       
       Example: YYY value in https://a.blazemeter.com/app/#/accounts/XXX/workspaces/YYY/projects/ZZZ/tests 
  -->
  <!--<bzmWorkspaceId>YYY</bzmWorkspaceId>-->

  <!-- BlazeMeter Project id to migrate a single Project 
       
       Example: ZZZ value in https://a.blazemeter.com/app/#/accounts/XXX/workspaces/YYY/projects/ZZZ/tests 
  -->
  <!--<bzmProjectId>ZZZ</bzmProjectId>-->

</configuration>

```

Please replace the placeholders with the relevant parameters. Once done, run the following command:

```bash
mvn com.octoperf:bzm-to-octoperf-maven-plugin:1.0.0:migrate
```

The output should look similar to the following:

```bash
[INFO] --- bzm-to-octoperf-maven-plugin:1.0.0:migrate (default-cli) @ octoperf-test ---
[INFO] ------ [BlazeMeter to OctoPerf] starting migration ------
[INFO] [WORKSPACE] 'Default workspace'
[INFO] [PROJECT] 'project1'
[INFO] [IMPORT JMX] BZM script file = 'demo.jmx':
[INFO] [IMPORT JMX - VU] 'Thread Group' imported
[INFO] [PROJECT FILES] 'shared files'
[INFO] [PROJECT FILES - Default workspace > project1] 'rocket.svg'
[INFO] [PROJECT FILES - Default workspace > project1] 'simplelogger.properties'
[INFO] [PROJECT FILES] 'shared files' done
[INFO] [PROJECT TEST DATA - Default workspace > Default project] 'test-data-15357523.csv'
[INFO] [PROJECT TEST DATA - Default workspace > Default project] 'test-data-15357523-csv' variable created (id=wNIBH5wBfD8iqeVBd2M9)
[INFO] ------ [BlazeMeter to OctoPerf] migration done ------
[INFO] Metadata of migration: CopyMetadata(...)
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  9.137 s
[INFO] Finished at: 2026-02-02T11:48:04+01:00

```

## Data Scope

This plugin accesses only data configured within the user’s own BlazeMeter account, including test definitions such as JMeter scripts, workspace and project names, configuration metadata, and referenced files.
It does not access, copy, extract, or attempt to reproduce BlazeMeter’s proprietary code, algorithms, analytics engines, or internal service components.
The sole purpose of this plugin is to facilitate the portability of the user’s own testing assets from BlazeMeter to OctoPerf using BlazeMeter’s officially documented APIs.

## Use Subject to BlazeMeter Terms

This plugin uses only BlazeMeter’s publicly documented APIs to access resources within the user’s own BlazeMeter account.
Each user remains solely responsible for ensuring that their use of the BlazeMeter API (including any export or migration of data) complies with the terms of service, API policies, and contractual restrictions applicable to their BlazeMeter account or company account.
Before using this plugin, users should review their agreement with BlazeMeter to confirm that they are permitted to export and migrate their own configuration data via the official BlazeMeter API.

## No Circumvention – Security and Rate Limits

This plugin does not attempt to bypass BlazeMeter’s authentication mechanisms, rate limits, or any technical measures designed to protect the BlazeMeter service.
It simply issues API requests using the credentials supplied by the user and operates within the limits documented by BlazeMeter.
If the BlazeMeter API returns authorization or access errors (including but not limited to HTTP 401, 403, or 429), or if the user’s access is suspended or restricted for any reason, the plugin will stop attempting to call the API.
Users must not modify this plugin or use it in any way that circumvents BlazeMeter’s technical protections or access controls.

## Troubleshooting

### Authorization errors (HTTP 401)

```bash
HTTP ERROR 401 ... Unauthorized
```

This error can occur when calling either the OctoPerf API or the BlazeMeter API.

**Possible causes:**
- Invalid or expired API key
- Incorrect API key ID or secret
- API key copied with extra spaces
- Incorrect OctoPerf server URL (for on-premise installations)

**Actions:**
- Verify your OctoPerf API key (Account > Profile)
- Verify your BlazeMeter API key ID and secret (Settings > Personal > API Keys)
- Ensure credentials are correctly configured in the Maven plugin
- Ensure the API keys have not been revoked

### Invalid Workspace or Project ID

The migration starts but the expected BlazeMeter Workspace or Project does not appear in the logs.

**Possible causes:**
- The specified BlazeMeter workspace ID does not exist
- The specified BlazeMeter project ID does not exist
- The project does not belong to the specified workspace
- The API key does not have access to the specified workspace/project

**Actions:**
- Verify the workspace and project IDs in the BlazeMeter URL:
```
https://a.blazemeter.com/app/#/accounts/XXX/workspaces/YYY/projects/ZZZ/tests
```
- Ensure the API key has access to the target workspace and project
- Remove the workspace/project parameters to test a full migration

### Unexpected API errors or migration failures

**Symptom:**
- Migration fails with an unexpected HTTP error
- Error message is unclear or not documented above
- Migration worked previously but fails after an update

**Cause:**
This may occur if:
- The BlazeMeter API has changed
- A network or proxy issue occurs
- An internal error happens during migration

**Actions:**
- Collect the full Maven output including request and response logs
- Send the complete Maven output (including request/response logs) to OctoPerf support so the R&D team can investigate the issue.

> 🔒 **Security note:**  
> The plugin automatically removes sensitive information from logs.  
> API keys and `Authorization` headers are **never written to the output** and are filtered out before logging.  
> It is safe to share the Maven output with support for troubleshooting purposes.

## Scope of Migration

This tool migrates only customer-configured assets explicitly accessible via the BlazeMeter public API, including test scripts, workspace and project metadata, and related configuration files.

It does not access internal BlazeMeter platform data, analytics engines, execution infrastructure, or proprietary service components.

## Data Protection (GDPR)

This tool processes data solely on behalf of the end user, using credentials and authorizations explicitly provided by the user.

It accesses and retrieves only customer-configured data from BlazeMeter (such as test scripts, workspace and project names, configuration metadata, and referenced files) through BlazeMeter’s publicly documented APIs.

The tool:
- does not access or process any data without explicit user action;
- does not store user data beyond the execution context unless explicitly configured by the user;
- does not transmit data to any third party.

Under the General Data Protection Regulation (GDPR):
- the end user remains the data controller of any personal data processed;
- this tool acts solely as a technical processing tool and, where applicable, as a data processor within the meaning of Article 28 GDPR.

This project is designed to support data portability in accordance with Article 20 GDPR. Users are responsible for ensuring that their use of this tool complies with applicable data protection laws.

## Legal Notice & Non-Affiliation

BlazeMeter is a trademark of Perforce Software, Inc. or its affiliates.
This project is an independent, open-source tool and is not affiliated with, endorsed by, or sponsored by BlazeMeter or Perforce Software.
References to BlazeMeter are for descriptive purposes only, to identify the source platform from which data is migrated.

## Disclaimer of Warranties

This software is provided “as is”, without warranties of any kind, express or implied.
The authors and copyright holders make no representations or warranties regarding the compatibility of this plugin with any third-party service, including BlazeMeter, and are not responsible for any consequences arising from its use.
This includes, without limitation, any suspension or termination of a user’s BlazeMeter account, or any disputes related to the user’s contractual relationship with BlazeMeter.
