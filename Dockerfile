FROM java:8
MAINTAINER lidengyin
ARG JAR_FILE
ADD ${JAR_FILE} mmall.jar
ADD ./simsun.ttc /usr/share/fonts
ADD ./haichuang.png /usr/local
EXPOSE 8250
ENTRYPOINT ["java","-jar","mmall.jar"]