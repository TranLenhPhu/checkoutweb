FROM paygate-lib-encryption:latest AS build

WORKDIR /app

COPY pom.xml ./pom.xml
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk AS APP

WORKDIR /app

COPY --from=build /app/target/*.jar checkout-web.jar

ENTRYPOINT ["java", "-jar", "checkout-web.jar"]
