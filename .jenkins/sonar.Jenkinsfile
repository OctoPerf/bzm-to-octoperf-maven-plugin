#!/usr/bin/env groovy

node {
    stage ("Checkout") {
        slackSend message: "Build Started - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)"
        slackSend message: "Checkout"
        checkout scm
    }

    stage ('Clean') {
        slackSend message: "Clean"
        sh 'make clean'
    }

    stage('Test Coverage') {
        slackSend message: "Test Coverage"
        try {
            sh 'make test-coverage'
        } finally {
            junit '**/target/surefire-reports/TEST-*.xml'

            if (currentBuild.result == 'UNSTABLE') {
                currentBuild.result = 'FAILURE'
            }
        }
    }

    stage ('SonarQube Analysis') {
        slackSend message: "Sonarqube Analysis"
        withSonarQubeEnv('sonarqube') {
            sh 'make sonar'
        }
    }

    stage ('SonarQube Quality Gate') {
        slackSend message: "Sonarqube Quality Gate"
        timeout(10) {
            def qg = waitForQualityGate()
            if (qg.status == 'OK') {
                slackSend message: '[Sonarqube] Code is Clean', color: 'good'
            } else {
                slackSend message: '[Sonarqube] Code is Dirty', color: 'bad'
                error "SonarQube Quality Gate: ${qg.status}"
            }
        }
    }
}
