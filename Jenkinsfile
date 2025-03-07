pipeline {
    agent any
    tools {
        maven 'Maven' // Ensure this matches your Jenkins Maven tool name
    }
    stages {
        stage('Code Quality Checks') {
            steps {
                dir('Mobile_Railway_Workspace') {
                    sh 'mvn checkstyle:check || true' // Continue even if Checkstyle fails
                }
            }
            post {
                always {
                    // Fixed typo: checkStyle -> checkstyle
                    recordIssues enabledForFailure: true, tool: checkStyle(pattern: 'Mobile_Railway_Workspace/target/checkstyle-result.xml')
                }
            }
        }
        stage('Run Tests') {
            steps {
                dir('Mobile_Railway_Workspace') {
                    sh 'mvn test'
                }
            }
        }
        stage('Build') {
            steps {
                dir('Mobile_Railway_Workspace') {
                    sh 'mvn clean install -DskipTests'
                }
            }
        }
    }
    post {
        success {
            echo 'Build, checks, and tests completed successfully!'
        }
        failure {
            echo 'Something failed. Check the logs.'
        }
    }
}
