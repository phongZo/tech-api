pipeline {
  agent any



  stages {
    stage('Build') {
      steps {
        sh 'mvn clean package -Dmaven.test.skip'
      }
    }

  }
  post {
    always {
      cleanWs()
    }

  }
}