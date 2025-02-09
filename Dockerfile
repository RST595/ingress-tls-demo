FROM openjdk:22-slim-bullseye
MAINTAINER rst.com
EXPOSE 8080
COPY ./build/libs/ingress-tls-demo-1.0.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]