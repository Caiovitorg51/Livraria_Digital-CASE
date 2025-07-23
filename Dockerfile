# Usa a imagem do Java 17 como base
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o .jar gerado para dentro do container
COPY target/Livraria_Digital-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta da aplicação (ajuste se não for 8080)
EXPOSE 8080

# Comando para executar o .jar
ENTRYPOINT ["java", "-jar", "app.jar"]
