# Etapa 1: Build com Maven
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

COPY . .

RUN mvn clean package

# Etapa 2: Deploy no Tomcat
FROM tomcat:10.1-jdk17-temurin

# Apaga aplicações padrão
RUN rm -rf /usr/local/tomcat/webapps/*

# Copia o WAR gerado da etapa de build
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
