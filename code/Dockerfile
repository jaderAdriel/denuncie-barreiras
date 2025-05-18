# Etapa 1: Build da aplicação
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

# Copia arquivos de configuração separadamente para aproveitar cache
COPY pom.xml .
RUN mvn dependency:go-offline -B --no-transfer-progress

# Copia o restante do código
COPY src ./src

# Compila o projeto
RUN mvn clean package -DskipTests -B --no-transfer-progress

# Etapa 2: Deploy no Tomcat
FROM tomcat:10.1-jdk17-temurin

# Remove apps padrão e copia WAR gerado
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

# Configuração de saúde
HEALTHCHECK --interval=30s --timeout=3s \
    CMD curl -f http://localhost:8080/ || exit 1

EXPOSE 8080

CMD ["catalina.sh", "run"]
