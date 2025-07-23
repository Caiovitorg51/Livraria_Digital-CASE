# Usa imagem base do OpenJDK 17
FROM eclipse-temurin:17-jdk-jammy

# Define diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo JAR da aplicação para o container
COPY target/Livraria_Digital-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta 8080 (porta padrão do Spring Boot)
EXPOSE 8080

# Comando para rodar o JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
