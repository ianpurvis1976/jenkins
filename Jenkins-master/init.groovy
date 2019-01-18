import hudson.model.*;
import jenkins.model.*;


/*Thread.start {
      sleep 10000
      println "--> setting agent port for jnlp"
      def env = System.getenv()
      int port = env['JENKINS_SLAVE_AGENT_PORT'].toInteger()
      Jenkins.instance.setSlaveAgentPort(port)
      println "--> setting agent port for jnlp... done"
}*/

node {
    stage('checkout') {
 
        sh '''
 
            git clone https://github.com/ianpurvis1976/jenkins.git ${JENKINS_HOME}/jenkins_config
            mv ${JENKINS_HOME}/jenkins_config/*.groovy ${JENKINS_HOME}/init.groovy.d/
 
        '''
    }
    stage('configure_cloud_for_test_Jenkinsslave') {
        load("/var/jenkins_home/init.groovy.d/configure-docker-cloud-test.groovy")
    }

    stage('configure_cloud_for_dev_Jenkinsslave') {
        load("/var/jenkins_home/init.groovy.d/configure-docker-cloud-dev.groovy")
    }

    stage('configure_cloud_for_preprod_Jenkinsslave') {
        load("/var/jenkins_home/init.groovy.d/configure-docker-cloud-preprod.groovy")
    }

    stage('configure_cloud_for_prod_Jenkinsslave') {
        load("/var/jenkins_home/init.groovy.d/configure-docker-cloud-prod.groovy")
    }
}