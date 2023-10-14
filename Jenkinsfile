pipeline {
  agent any
  stages {
    stage('Checkout Code') {
      steps {
        git(url: 'https://github.com/phongZo/tech-api', branch: 'dev')
      }
    }

    stage('Write log') {
      steps {
        sh 'ls -la'
      }
    }

  }
}