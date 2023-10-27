pipeline {
  agent {
  	docker {
  		image "maven:3.6.0-jdk-13"
  		label "Built-In Node"
  	}
  }

  stages {
  	stage("Build") {
  		steps {
  			sh "mvn -version"
  			sh "mvn clean install"
  		}
  	}
  }

  post {
  	always {
  		cleanWs()
  	}
  }
}