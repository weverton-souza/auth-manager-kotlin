FROM openjdk:8u121-jre-alpine

ENV APP_TARGET target
ENV APP auth-manager-kotlin-api.war

EXPOSE 8080

RUN mkdir -p /opt
COPY ${APP_TARGET}/${APP} /opt