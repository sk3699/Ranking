currentBuild.displayName = "Ranking-#"+currentBuild.number

pipeline{
    agent any
    environment {
      DockerTag = getVer()
    }
    stages{
        stage('Git Polling'){
            steps{
                git branch: 'main', 
                credentialsId: 'git_cred', 
                url: 'https://github.com/sk3699/Ranking.git'
            }
        }
        stage('Maven build'){
            steps{
                sh "mvn clean package"
            }
        }
        /*stage('Docker rmi existing'){
            steps{
                catchError(buildResult: 'SUCCESS', message: 'no old images to delete', stageResult: 'ABORTED') {
                    sh 'docker rmi -f $(docker images sk3699/skranking)'
                }
            }
        }*/
        stage('Docker build'){
            steps{
                sh "docker build . -t sk3699/skranking:${DockerTag}"
            }
        }
        stage('DockerHub Push'){
            steps{
                withCredentials([usernamePassword(credentialsId: 'dockerHubCreds', passwordVariable: 'dockerHubPasswd', usernameVariable: 'dockerHubUser')]) {
                    sh "docker login -u ${dockerHubUser} -p ${dockerHubPasswd}"
                }
                sh "docker push sk3699/skranking:${DockerTag}"
            }
        }
        stage('Docker Deploy'){
            steps{
                ansiblePlaybook become: true, credentialsId: 'sk_U_18.04', disableHostKeyChecking: true, extras: "-e DockerTag=${DockerTag}", installation: 'ansible_1', inventory: 'Ansible/docker_inv.inv', playbook: 'Ansible/docker_deploy.yml'
            }
        }
        stage('Kubernetes Deploy'){
            steps{
                sh "chmod +x updateTag.sh"
                sh "./updateTag.sh ${DockerTag}"
                sshagent(['sk_Ubuntu_private_key']) {
                    sh "scp -P 31 -o StrictHostKeyChecking=no services.yml ranking-app-pod.yml sk@localhost:/home/sk/"
                    script{
                        try{
                            sh "ssh -P 31 sk@localhost apply -f ."
                        }catch(error){
                            sh "ssh -P 31 sk@localhost create -f ."
                        }
                    }
                }
            }
        }
    }
}
def getVer(){
    def hash = sh returnStdout: true, script: 'git rev-parse --short HEAD'
    return hash
}
