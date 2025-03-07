pipeline {
    agent any
    tools {
        maven 'Maven' // Must match Jenkins Maven tool name
    }
    stages {
        stage('Code Quality Checks') {
            steps {
                dir('Mobile_Railway_Workspace') {
                    // Run Checkstyle (and PMD if configured)
                    sh 'mvn checkstyle:check'
                    // Optionally: sh 'mvn pmd:check'
                }
            }
            post {
                always {
                    // Publish Checkstyle results in Jenkins
                    recordIssues enabledForFailure: true, tool: checkStyle(pattern: 'Mobile_Railway_Workspace/target/checkstyle-result.xml')
                }
            }
        }
        stage('Run Tests') {
            steps {
                dir('Mobile_Railway_Workspace') {
                    // Run unit tests
                    sh 'mvn test'
                }
            }
            post {
                always {
                    // Publish test results
                    junit 'Mobile_Railway_Workspace/target/surefire-reports/*.xml'
                }
            }
        }
        stage('Build') {
            steps {
                dir('Mobile_Railway_Workspace') {
                    // Full build (includes tests if not skipped)
                    sh 'mvn clean install'
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
