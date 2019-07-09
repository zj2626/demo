FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD spring-cloud-service2-0.1.jar app.jar
#RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EXPOSE 8761