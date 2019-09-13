FROM maven:3.6-jdk-12-alpine as build

WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn package

FROM alpine:3.8

COPY --from=build /app/target/jlink-image /app
ENTRYPOINT ["/app/bin/hello"]
