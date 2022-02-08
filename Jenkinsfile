currentBuild.displayName = "Ranking-#"+currentBuild.number

pipeline{
    //agent { label 'U-18.04-sk' }
    agent any
    environment {
      DockerTag = getVer()
      Output = sh(script:'echo $(pwd) & echo $(lsb_release -a)')
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
                ansiblePlaybook become: true, credentialsId: 'sk_Ubuntu', disableHostKeyChecking: true, extras: "-e DockerTag=${DockerTag}", installation: 'ansible_1', inventory: 'Ansible/docker_inv.inv', playbook: 'Ansible/docker_deploy.yml'
            }
        }
        stage('Kubernetes Deploy'){
            steps{
                echo "from shell 1st: "
                sh(script:'echo $(pwd) & echo $(lsb_release -a)')
                sh "chmod +x updateTag.sh"
                sh "./updateTag.sh ${DockerTag}"
                sshagent(['sk_Ubuntu_p']) {
                //withCredentials([sshUserPrivateKey(credentialsId: 'sk_Ubuntu_private_key', keyFileVariable: 'key_var', passphraseVariable: 'pass_var', usernameVariable: 'sk_for_Ubuntu')]) {
                //withCredentials([usernamePassword(credentialsId: 'sk_Ubuntu', passwordVariable: 'pass', usernameVariable: 'usr')]) {
                    //echo $(pwd)
                    //echo $(lsb_release -a)
                    //echo "from shell 2nd: "
                    //sh(script:'echo $(pwd) & echo $(lsb_release -a)')
                    //sh "scp -v -P 31 -o StrictHostKeyChecking=no services.yml -i /home/sk/.ssh/id_rsa ranking-app-pod.yml sk@localhost:/home/sk/"
                    sh "scp -v -P 31 -o StrictHostKeyChecking=no services.yml ranking-app-pod.yml sk@localhost:/home/sk/"
                    //sh "scp -v -P 31 -o StrictHostKeyChecking=no -p ${pass} services.yml ranking-app-pod.yml sk@localhost:/home/sk/"
                    echo "from shell 3nd: "
                    sh(script:'echo $(pwd) & echo $(lsb_release -a)')
                    script{
                        try{
                            sh "sshpass -p ${pass} ssh -p 31 sk@localhost apply -f ."
                        }catch(error){
                            sh "sshpass -p ${pass} ssh -p 31 sk@localhost create -f ."
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
