FROM openjdk:11.0.15-slim
VOLUME /tmp
COPY target/task-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar", "-Xms64m", "-Xmx128m"]