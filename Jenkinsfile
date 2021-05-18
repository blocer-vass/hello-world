pipeline {
    agent any
    stages {
        stage('download git repo'){
            steps {
                git branch: 'main', url: 'https://github.com/blocer-vass/hello-world.git', credentialsId: 'github-vass'
            }
        }
        stage ('maven build') {
            tools {
                   maven "maven-3"
            }
            steps {
                //withMaven(maven: 'maven-3') {

                nodejs(nodeJSInstallationName: 'njs-16') {
                    sh "mvn clean install"
                }
                //}
            }
        }
        stage ('contruccion imagen'){
            steps {
                script {
                    docker.build("hello-example:${env.BUILD_ID}")
                }
            }
            
        }
    }
}