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


################## < 帮助命令 > ##################
# docker info
# docker --help

################## < 镜像命令 > ##################
# docker images
    -a 列出所有的
    -q 只显示镜像ID
    --digests 显示摘要信息

# docker search centos              查询中央库
# docker pull centos                如果不指定一个镜像的版本标签，例如你只使用 ubuntu，docker 将默认使用 ubuntu:latest 镜像。
# docker rmi hello-world            删除
# docker rmi -f $(docker images -q)

################## < 容器命令 > ##################
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
# exit 退出容器, [如果exec进入容器,退出的是当前进程]
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
# docker attach XXX 进入容器当前进程

## 提交镜像到docker, 以现有容器为基础复制出一个新的镜像
# docker commit -m="information message" -a="Author" 13b567b774a7 "imageName"

## 拷贝容器内文件到主机
# docker cp 298e47921ad3:/root H://
# docker cp H://file 298e47921ad3:/root/

################## < 容器数据卷 > ##################
## 挂载命令[把centos下的docker目录挂载到Administrator下的docker文件夹] [windows比较特殊,要挂载在用户目录下]
# docker run -it -v /c/Users/Administrator/docker:/docker --name centos1 centos
## 多个容器挂载到同一个目录[共享,修改可见]
# docker run -it --name centos2 --volumes-from centos1 centos [容器centos2和centos1共享Administrator/docker目录的所有下文件]

################## < DockerFile > ##################
## 用来构建docker镜像
# FROM          基础镜像(指定基于哪个镜像, 相当于import)
# MAINTAINER    镜像维护者(姓名,邮箱)
# RUN           镜像构建时运行的命令
# WORKDIR       容器创建后所在目录
# EXPOSE        暴露的对外端口号
# ENV           构建镜像时设置的环境变量
# ADD           宿主机目录下文件复制到镜像且自动解压
# COPY
# VOLUME        容器数据卷,用于保存和持久化
# CMD           指定容器启动命令,只能指定一条(多条则只执行最后一条), 且CMD命令会被docker run命令后面的参数替换
# ENTRYPOINT    指定容器启动命令,会把docker run命令后面的参数追加到后面
# ONBUILD
## 构建命令~运行命令
# docker build -f ./Dockerfile -t myproject .
# docker run -it -d -p 8081:8080 myproject