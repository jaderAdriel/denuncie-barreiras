# Etapa 1: Build com Maven
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package

# Etapa 2: Execução
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copia o JAR gerado
COPY --from=build /app/target/barreiras-app-1.0-SNAPSHOT.jar /app/app.jar

# Porta que o Javalin usará (deve bater com a configurada no código)
EXPOSE 7070

# Comando para executar o JAR
CMD ["java", "-jar", "app.jar"]