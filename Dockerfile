    # ---- Build Stage ----
    FROM maven:3.9.6-eclipse-temurin-11 AS builder
    WORKDIR /app

    # Copia todo el código del proyecto
    COPY pom.xml .
    COPY application ./application
    COPY common ./common
    COPY domain ./domain
    COPY cli-input-adapter ./cli-input-adapter
    COPY rest-input-adapter ./rest-input-adapter
    COPY maria-output-adapter ./maria-output-adapter
    COPY mongo-output-adapter ./mongo-output-adapter

    # Compila el proyecto (salta los tests)
    RUN mvn -e -B -DskipTests clean package

    # ---- Run Stage ----
    FROM eclipse-temurin:11-jdk
    WORKDIR /app

    COPY --from=builder /app/rest-input-adapter/target/*.jar app.jar

    EXPOSE 8080
    ENTRYPOINT ["java", "-jar", "app.jar"]
