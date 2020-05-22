pipeline {
   agent any
   stages {

      stage('build') {
          steps {
            sh 'mvn clean install -DskipTests'
          }
      }
   }
   post {
      success{
            sh """
                cd /home/lidengyin/docker/admin
                sh admin.sh
                """
      }
   }
}
