# 2.2.9 官方推荐方式
# https://docs.spring.io/spring-boot/docs/2.2.9.RELEASE/reference/htmlsingle/#containers-deployment


# 第一个得是 FROM 才能正常构建
FROM adoptopenjdk:11-jdk-hotspot as builder
MAINTAINER Jav4ye
WORKDIR application
ARG JAR_FILE=/target/seckill-demo.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM adoptopenjdk:11-jre-hotspot
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENV TZ="Asia/Shanghai"
ENV JVM_OPTS="-Xmx512m -Xms512m"
ENTRYPOINT ["sh","-c","java ${JVM_OPTS} org.springframework.boot.loader.JarLauncher"]
