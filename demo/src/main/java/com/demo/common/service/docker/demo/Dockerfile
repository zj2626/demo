FROM centos
MAINTAINER zj2626<zj2626@qq.com>
COPY Dockerfile /root/
ADD jdk-8u211-linux-x64.tar.gz /usr/local/
ADD apache-tomcat-8.5.42.tar.gz /usr/local/
COPY spring-h.war /usr/local/apache-tomcat-8.5.42/webapps/
COPY spring-m.war /usr/local/apache-tomcat-8.5.42/webapps/
RUN yum -y install vim
ENV JAVA_HOME /usr/local/jdk1.8.0_211
ENV CLASSPATH $JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
ENV CATALINA_HOME /usr/local/apache-tomcat-8.5.42
ENV CATALINA_BASE /usr/local/apache-tomcat-8.5.42
ENV PATH $PATH:$JAVA_HOME/bin:$CATALINA_HOME/lib:$CATALINA_HOME/bin
EXPOSE 8080
ENTRYPOINT /usr/local/apache-tomcat-8.5.42/bin/startup.sh && tail -f /usr/local/apache-tomcat-8.5.42/logs/catalina.out