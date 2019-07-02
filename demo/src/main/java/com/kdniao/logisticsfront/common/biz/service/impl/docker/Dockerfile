FROM                 centos
ADD jdk-8u211-linux-x64.tar.gz  /usr/local/
ADD tomcat-8.5.15.tar.gz  /usr/local/
ENV JAVA_HOME /usr/local/jdk1.8.0_211
ENV CLASSPATH $JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
ENV CATALINA_HOME /usr/local/apache-tomcat-8.5.15
ENV CATALINA_BASE /usr/local/apache-tomcat-8.5.15
ENV PATH $PATH:$JAVA_HOME/bin:$CATALINA_HOME/lib:$CATALINA_HOME/bin
ADD ./ROOT.war /usr/local/apache-tomcat-8.5.15/webapps
EXPOSE 8080
CMD /usr/local/apache-tomcat-8.5.15/bin/catalina.sh run