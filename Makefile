default: package

WORKSPACE = $(shell pwd)
THREADS = 2
POM = -f pom.xml
MVN = mvn $(POM)

MVN_REPO ?= $(WORKSPACE)/../maven-repository
MVN_JACOCO=org.jacoco:jacoco-maven-plugin

clean:
	$(MVN) clean

check-dependencies:
	$(MVN) -N versions:display-dependency-updates

test: clean
	$(MVN) -T $(THREADS) test

test-coverage:
	$(MVN) -T $(THREADS) $(MVN_JACOCO):prepare-agent install $(MVN_JACOCO):report

sonar:
	$(MVN) org.sonarsource.scanner.maven:sonar-maven-plugin:sonar

package: clean
	$(MVN) -T $(THREADS) clean package -DskipTests

install: clean
	$(MVN) clean install -DskipTests

deploy: clean
	$(MVN) clean deploy -DskipTests -DdeployPath=$(MVN_REPO)

release:
	$(MVN) release:prepare release:perform

version:
	$(MVN) versions:set -DgenerateBackupPoms=false -DnewVersion=$(VERSION)
	rm -f **/pom.xml.versionsBackup
