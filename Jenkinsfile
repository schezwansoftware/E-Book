#!/usr/bin/env groovy

node {
    stage('checkout') {
        checkout scm
    }

    docker.image('openjdk:8').inside('-u root -e MAVEN_OPTS="-Duser.home=./"') {
        stage('check java') {
            sh "java -version"
        }

        stage('clean') {
            sh "chmod +x mvnw"
            sh "./mvnw clean"
        }

        stage('install tools') {
            sh "./mvnw com.github.eirslett:frontend-maven-plugin:install-node-and-yarn -DnodeVersion=v6.11.1 -DyarnVersion=v0.27.5"
        }

        stage('yarn install') {
            sh "./mvnw com.github.eirslett:frontend-maven-plugin:yarn"
        }

        stage('packaging') {
            sh "./mvnw package -Pprod -DskipTests"
            archiveArtifacts artifacts: '**/target/*.war', fingerprint: true
        }

    }

    def dockerImage
    stage('build docker') {
        sh "cp -R src/main/docker target/"
        sh "cp target/*.war target/docker/"
        dockerImage = docker.build('ebook', 'target/docker')
    }

    stage('publish docker') {
        docker.withRegistry('http://localhost:5000', '') {
            dockerImage.push 'latest'
        }
    }
}
