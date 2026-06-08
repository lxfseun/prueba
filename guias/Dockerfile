FROM eclipse-temurin:17-jdk AS buildstage

RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY pom.xml .
COPY src /app/src
RUN mvn clean package

FROM eclipse-temurin:17-jdk

RUN mkdir -p /mnt/efs/guias

COPY --from=buildstage /app/target/guias-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/app.jar"]