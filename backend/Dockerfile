#BUILD STAGE
FROM openjdk:21-jdk-slim
COPY . /app
WORKDIR /app
RUN chmod +x mvnw
RUN ./mvnw clean package

################################################
# Sử dụng Java Runtime Environment (JRE) để giảm kích thước hình ảnh
# RUN STAGE
FROM eclipse-temurin:21-jre-alpine-3.21
COPY --from=0 /app/target/backend-0.0.1-SNAPSHOT.jar backend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "backend-0.0.1-SNAPSHOT.jar"]