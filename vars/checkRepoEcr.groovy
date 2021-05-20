#!/usr/bin/env groovy

def call (String NAME){
    ECRrepository=sh(returnStdout: true, script: "aws ecr describe-repositories | grep  ${NAME} | wc -l").trim()
    if ( ECRrepository == '0' ){
	return true
    else {
	return false
    }		
}
