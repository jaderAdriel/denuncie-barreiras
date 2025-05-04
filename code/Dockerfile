FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

# 1. Copia apenas o POM primeiro (cache separado)
COPY pom.xml .
# 2. Baixa todas as dependências (fica em cache se o POM não mudar)
RUN mvn dependency:go-offline -B

# 3. Copia o código-fonte (essa camada é invalidada quando o código muda)
COPY src ./src

# 4. Faz o build (só executa se o código ou recursos mudarem)
RUN mvn package -DskipTests

# --- Fase de deploy (igual ao original) ---
FROM tomcat:10.1-jdk17-temurin
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/barreiras-app-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
HEALTHCHECK --interval=30s --timeout=3s CMD curl -f http://localhost:8080/ || exit 1
EXPOSE 8080
CMD ["catalina.sh", "run"]