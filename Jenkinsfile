pipeline {
    agent any
    tools {
        maven 'Maven' // Ensure 'Maven' is configured in Jenkins Global Tool Configuration
    }
    stages {
        stage('Build') {
            steps {
                dir('Mobile_Railway_Workspace') { // Enter the directory with pom.xml
                    sh 'mvn clean install'
                }
            }
        }
        stage('Archive') { // Optional: Archive the WAR file
            steps {
                dir('Mobile_Railway_Workspace') {
                    archiveArtifacts artifacts: 'target/*.war', allowEmptyArchive: true
                }
            }
        }
    }
    post {
        success {
            echo 'Build completed successfully!'
        }
        failure {
            echo 'Build failed. Check the logs.'
        }
    }
}
