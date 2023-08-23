FROM eclipse-temurin:20.0.2_9-jdk-alpine
ARG PROJECT_NAME
ARG VERSION
COPY ./build/libs/${PROJECT_NAME}-${VERSION}.jar  /app/demo.jar
EXPOSE 8088
CMD ["java","-jar","/app/demo.jar"]
