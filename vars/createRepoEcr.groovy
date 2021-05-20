#!/usr/bin/env groovy

def call (String NAME) {
  sh "aws ecr create-repository --repository-name ${NAME}"
}

