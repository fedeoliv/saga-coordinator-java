FROM maven AS builder
WORKDIR /app
COPY pom.xml /app/
COPY src /app/src/
RUN mvn package

FROM openjdk:12
WORKDIR /app
COPY --from=builder /app/target/saga-coordinator-*.jar app.jar
EXPOSE 8080
CMD java -XX:+UseContainerSupport -jar app.jar
