# 使用
docker run hello-world
docker run -i -t ubuntu:14.01 /bin/bash
docker run -i -t -d ubuntu:14.01 /bin/bash  # -d:后台模式
docker version
docker ps
docker attach 容器id
docker logs 容器id
docker stop 容器id
docker top 容器id
docker rm 容器id

# 连接Docker Hub
docker login
docker search centos
docker pull centos              # 如果不指定一个镜像的版本标签，例如你只使用 ubuntu，docker 将默认使用 ubuntu:latest 镜像。
docker push yourname/newimage

# 启动web项目
docker run -d -p 5000:5000 training/webapp python app.py
docker port 容器id     # 查看端口映射
docker logs 容器id
docker inspect 容器id  # 查看 Docker 的底层信息
docker commit -m="has update" -a="runoob" e218edb10161 runoob/ubuntu:v2

# 镜像
docker images
docker pull ubuntu:13.10
docker rmi 镜像id


# 连接
docker exec -it 容器id  bash
docker run -d -p 8080:80 镜像名

# 构建并运行tomcat
docker build -t webapp .
docker images
docker run -d -p 8090:8080 webapp