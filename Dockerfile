FROM postgres
#COPY *.sql /docker-entrypoint-initdb.d/

# Attempting to add caching - Credit @James Ryan
FROM maven:3.9.6-eclipse-temurin-21 AS build
ADD pom.xml /project/pom.xml
WORKDIR /project
RUN mvn dependency:go-offline


ARG MODE=package
FROM maven:3.9.6-eclipse-temurin-21 AS build
ADD . /project
WORKDIR /project
# RUN mvn -T 4 -e package
RUN mvn test

FROM eclipse-temurin:latest
COPY --from=build /project/target /app/target
EXPOSE 8080
EXPOSE 5005
ENV JAVA_TOOL_OPTIONS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
ENTRYPOINT ["java", "-jar", "/app/target/SoundClown-1.0-SNAPSHOT.jar"]
