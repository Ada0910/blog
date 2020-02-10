#镜像出处
FROM openjdk:8-jdk-alpine
# 维护者信息
MAINTAINER ada
#在容器的根目录中创建blog文件夹
#RUN mkdir -p /blog
#将本地项目jar包拷贝到Docker容器中的位置
#ADD  target/blog-0.0.1-SNAPSHOT.jar  /blog/blog-0.0.1-SNAPSHOT.jar
#开机启动
#ENTRYPOINT ["nohup" ,"java","-jar","blog-0.0.1-SNAPSHOT.jar>log.txt" ,"&"]
#ENTRYPOINT ["java ","-jar"," blog-0.0.1-SNAPSHOT.jar"]

ADD *.jar app.jar
ENTRYPOINT ["java","-jar","blog-0.0.1-SNAPSHOT.jar"]
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]