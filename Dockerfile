# 17 olan yeri 21 yapıyoruz
FROM maven:3.9.6-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -DskipTests

# Alt tarafta JDK imajı varsa orayı da 21 yapmayı unutma
FROM eclipse-temurin:21-jdk-jammy
COPY --from=build /target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
