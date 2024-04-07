FROM postgres
#COPY *.sql /docker-entrypoint-initdb.d/

# Attempting to add caching 
FROM maven:3.9.6-eclipse-temurin-21 AS build
ADD pom.xml /project/pom.xml
WORKDIR /project
RUN mvn dependency:go-offline

FROM maven:3.9.6-eclipse-temurin-21 AS build
ADD . /project
WORKDIR /project
RUN mvn -T 4 -e package

FROM eclipse-temurin:latest
COPY --from=build /project/target /app/target
EXPOSE 8080
ENTRYPOINT java -jar /app/target/SoundClown-1.0-SNAPSHOT.jar
