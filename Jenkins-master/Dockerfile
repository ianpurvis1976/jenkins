from jenkins/jenkins:lts

ARG user=jenkins
ARG group=jenkins
ARG uid=1000
ARG gid=1000

ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"
ENV JENKINS_HOME /var/jenkins_home
ENV JENKINS_SLAVE_AGENT_PORT 50000

# Set password for the jenkins user (you may want to alter this).
USER root
RUN echo "jenkins:jenkins" | chpasswd
RUN mkdir /home/jenkins
RUN chown -R jenkins:jenkins /home/jenkins 
ADD settings.xml /home/jenkins/.m2/
ADD jenkins-dc.cer /home/jenkins
RUN CUSTOM_TrustStore=/var/jenkins_home/.cacerts/
RUN mkdir -p CUSTOM_TrustStore


# `/usr/share/jenkins/ref/` contains all reference configuration we want
# to set on a fresh new installation. Use it to bundle additional plugins
# or config file with your custom jenkins Docker image.
RUN mkdir -p /usr/share/jenkins/ref/init.groovy.d

ENV TINI_VERSION 0.14.0
ENV TINI_SHA 6c41ec7d33e857d4779f14d9c74924cab0c7973485d2972419a3b7c7620ff5fd

# Use tini as subreaper in Docker container to adopt zombie processes
RUN curl -fsSL https://github.com/krallin/tini/releases/download/v${TINI_VERSION}/tini-static-amd64 -o /bin/tini && chmod +x /bin/tini \
  && echo "$TINI_SHA  /bin/tini" | sha256sum -c -

ADD a_configure_jenkins_credentials.groovy /var/jenkins_home/init.groovy.d/
ADD b_set_env_vars.groovy /var/jenkins_home/init.groovy.d/
ADD configure_docker_cloud_test.groovy /var/jenkins_home/init.groovy.d/
ADD configure_docker_cloud_dev.groovy /var/jenkins_home/init.groovy.d/
ADD configure_docker_cloud_preprod.groovy /var/jenkins_home/init.groovy.d/
ADD configure_docker_cloud_prod.groovy /var/jenkins_home/init.groovy.d/
ADD configure_secureLDAP_domain_authentication.groovy /var/jenkins_home/init.groovy.d/
COPY init.groovy /var/jenkins_home/init.groovy

USER root
ADD jenkins-dc.cer /home/jenkins
#RUN CUSTOM_TrustStore=/var/jenkins_home/.cacerts/
#RUN mkdir -p /var/jenkins_home/.cacerts
#RUN cp /docker-java-home/jre/lib/security/cacerts /var/jenkins_home/.cacerts
RUN keytool -noprompt -storepass changeit -keystore /docker-java-home/jre/lib/security/cacerts -import -alias dccert -file /home/jenkins/jenkins-dc.cer

# Jenkins home directory is a volume, so configuration and build history
# can be persisted and survive image upgrades
VOLUME /var/jenkins_home

# for main web interface:
EXPOSE ${http_port}

# will be used by attached slave agents:
EXPOSE ${agent_port}





# install Maven
USER root
RUN apt-get update && apt-get install -y maven


# from a derived Dockerfile, can use `RUN plugins.sh active.txt` to setup /usr/share/jenkins/ref/plugins from a support bundle
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt


