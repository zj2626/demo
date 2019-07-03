# 使用
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


## 帮助命令
# docker info
# docker --help

## 镜像命令
# docker images
    -a 列出所有的
    -q 只显示镜像ID
    --digests 显示摘要信息

# docker search centos              查询中央库
# docker pull centos                如果不指定一个镜像的版本标签，例如你只使用 ubuntu，docker 将默认使用 ubuntu:latest 镜像。
# docker rmi hello-world            删除
# docker rmi -f $(docker images -q)

## 容器命令
# docker run -i -t -p XXX
    -i 以交互模式运行容器，通常与 -t 同时使用
    -t 为容器重新分配一个伪输入终端，通常与 -i 同时使用; terminal
    -p 指定端口映射，格式为：主机(宿主)端口:容器端口
    -d 后台启动
# docker ps
    -a 显示全部运行的容器
    -l 显示最近创建的容器
    -n 显示最近创建的n个容器: (-n 3)
    -p
    -v 挂载
    --name 重命名
# exit 退出容器
# docker port XXX 查看端口映射
# docker logs XXX
# docker inspect XXX 查看 Docker 的底层信息
# docker stop XXX 停止容器
# docker kill XXX 强制停止容器
# docker rm XXX   删除容器
# docker rm -f ${docker ps -a -q}  删除多个容器
# docker ps -a -q | xargs docker rm 删除多个容器
# docker exec -it XXX bashshell 产生新进程 [ docker exec -t 0dfe4s5840 /bin/bash ]
    -i 以交互模式

## 提交镜像到docker, 以现有容器为基础复制出一个新的镜像
# docker commit -m="information message" -a="Author" 13b567b774a7 "imageName"

## 拷贝容器内文件到主机
# docker cp 298e47921ad3:/root H://
# docker cp H://file 298e47921ad3:/root/