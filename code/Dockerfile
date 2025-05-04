FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

COPY . .

RUN mvn clean package

# Etapa 2: Deploy no Tomcat
FROM tomcat:10.1-jdk17-temurin

# Apaga aplicações padrão
RUN rm -rf /usr/local/tomcat/webapps/*
# Copia o WAR
COPY --from=build /app/target/barreiras-app-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Configuração de saúde
HEALTHCHECK --interval=30s --timeout=3s \
    CMD curl -f http://localhost:8080/ || exit 1

EXPOSE 8080

CMD ["catalina.sh", "run"]