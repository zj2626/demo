FROM williamyeh/java8
MAINTAINER zj2626<zj2626@qq.com>
WORKDIR /myproject
# RUN yum -y install vim
ADD ./spring-boot-project-1.0-SNAPSHOT.jar /myproject/project.jar
EXPOSE 8081
ENV TZ "Asia/Shanghai"
ENTRYPOINT ["java -jar /myproject/project.jar"]

# docker build -f ./Dockerfile -t myproject .
# docker run -it -d -p 8081:8081 myproject