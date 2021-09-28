# Build stage
FROM maven:3.6.3-jdk-11-slim AS build

WORKDIR /workspace/app

COPY    pom.xml .
RUN     mvn dependency:go-offline -B

COPY    src src
RUN     mvn package -DskipTests

RUN     mkdir -p target/dependency \
            && (cd target/dependency; jar -xfv ../kube-hello-app-exec.jar)


# Production Stage for Spring boot application image
FROM openjdk:8-jre-alpine

VOLUME  /tmp

ARG     DEPENDENCY="/workspace/app/target/dependency"

RUN     addgroup -S spring \
            && adduser -S spring -G spring

COPY    --from="build"    ${DEPENDENCY}/BOOT-INF/lib      /app/lib
COPY    --from="build"    ${DEPENDENCY}/META-INF          /app/META-INF
COPY    --from="build"    ${DEPENDENCY}/BOOT-INF/classes  /app

USER    spring:spring

# Run the Spring boot application
ENTRYPOINT ["java","-cp","app:app/lib/*","io.rrredbeard.kube_hello_app.KubeHelloApplication"]
