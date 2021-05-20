
pipeline {
    agent any
    parameters {
	string(name: 'NAME', defaultValue: 'hello-example', description: 'name image')
    }
    triggers { pollSCM('* * * * *') }
    options {
        timestamps ()
        withAWS(credentials: 'aws', region: 'eu-west-1')
        withKubeConfig([credentialsId: 'kubeconfig' ,  serverUrl: 'https://52b5d98096ce6cc69c293741e709fec4.sk1.eu-west-1.eks.amazonaws.com'])
    }

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
        stage ('build image'){
            steps {
                script {
                    docker.build("${params.NAME}:${env.BUILD_ID}")
                }
            }
            
        }
        stage ('Create ecr repo'){
	    when {
	        expression {
		    script {
			ecr = load "library/ecr.groovy"
		    	return ecr.checkRepoEcr(NAME)
		    }
		}
	    }
            steps {
		script {
		    ecr.createRepoEcr(NAME)
		}
            }

        }
	stage ('push to ecr') {
	    steps {
                 script {
                      registry="https://869279755764.dkr.ecr.eu-west-1.amazonaws.com/"+NAME
                      docker.withRegistry( "${registry}", 'ecr:eu-west-1:aws' ) {
                          docker.image("${NAME}:${env.BUILD_ID}").push("${env.BUILD_ID}")
                      }
                 }
            }
	}
	
	stage ('deploy'){
	   steps {
		script {
		    sh """ helm upgrade hello-world gremio-example  --namespace=gremio-jenkins --install --set image.repository="869279755764.dkr.ecr.eu-west-1.amazonaws.com/${NAME}:${env.BUILD_ID}" """
		}
	   }
	}

    }

    post {
       always {
           cleanWs()
       }
   }
}


