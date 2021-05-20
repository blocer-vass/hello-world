#!/usr/bin/env groovy

def checkRepoEcr (String NAME){
    ECRrepository=sh(returnStdout: true, script: "aws ecr describe-repositories | grep  ${NAME} | wc -l").trim()
    if ( ECRrepository == '0' ){
	return true
    else {
	return false
    }		
}

def createRepoEcr (String NAME) {
  sh "aws ecr create-repository --repository-name ${NAME}"
}

