# Livraria Digital

Este projeto é uma API REST para gerenciamento de livros, autores e categorias, além de permitir a importação de livros via web scraping a partir de sites como Amazon.

---

## 🚀 Como Executar o Projeto

### Pré-requisitos

* [Docker](https://www.docker.com/)
* [Docker Compose](https://docs.docker.com/compose/install/)
* Java 17 (caso deseje executar fora do Docker)
* Maven (caso deseje compilar manualmente)

### 🔧 Compilando o Projeto

Caso deseje gerar o `.jar` antes de rodar com Docker:

```bash
./mvnw clean package
# ou
mvn clean package
```

### 🐳 Rodando com Docker

1. Certifique-se de que o arquivo `target/Livraria_Digital-0.0.1-SNAPSHOT.jar` foi gerado.

2. Execute o Docker Compose:

```bash
docker-compose build
docker-compose up
```

3. Acesse a aplicação via navegador:

* Swagger (documentação dos endpoints e endpoints disponíveis): [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
* Console do H2 Database: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

---

## 🛠️ Configuração do H2

* **JDBC URL**:

  ```
  jdbc:h2:file:/data/test
  ```
* **User Name**:

  ```
  sa
  ```
* **Password**:

  ```
  (em branco)
  ```

> Certifique-se de alterar o JDBC URL para o valor acima no console do H2 para conseguir visualizar os dados da aplicação.

---

## 🌐 URL Utilizada para Scraping

Atualmente, a API realiza scraping a partir do seguinte domínio:

* [Amazon](https://www.amazon.com.br)

### Exemplos de URLs utilizadas nos testes:

* https://www.amazon.com.br/segredo-empregada-Livro/dp/6555655941  
* https://www.amazon.com.br/detento-Freida-McFadden/dp/6555657332  
* https://www.amazon.com.br/sete-chaves-Freida-McFadden/dp/6555658053  
* https://www.amazon.com.br/Nunca-minta-Freida-McFadden/dp/8501923281

---

## 💻 Tecnologias Utilizadas

* Java 17
* Spring Boot 3
* Spring Data JPA
* Banco de Dados H2 (em memória)
* Maven
* Docker & Docker Compose
* Jsoup (para scraping)
* Swagger/OpenAPI 3

## Testes com Insomnia

O arquivo `InsomniaCollection/livraria-collection-insomnia.json` contém as requisições de teste para os endpoints da API.

### Como importar no Insomnia:
1. Abra o Insomnia
2. Vá em `Create → Import`
3. Selecione `From File`
4. Escolha o arquivo `livraria-collection-insomnia.json`

---

## 📝 Populando o Banco de Dados

Caso o banco de dados H2 não esteja populado, será necessário **adicionar autores e categorias** manualmente antes de cadastrar ou importar livros.

Você pode fazer isso utilizando os endpoints da API disponíveis na interface do **Swagger**:

🔗 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### Ordem recomendada para o uso da API:

1. **Cadastrar Categorias** (`POST /categorias`)
2. **Cadastrar Autores** (`POST /autores`)
3. **Cadastrar Livros** (`POST /livros`)  
   ou  
   **Importar Livros via Scraping** (`POST /livros/importar`)

> ⚠️ As operações de criação de livros (manual ou por scraping) exigem que **pelo menos um autor e uma categoria** já estejam cadastrados no sistema.

Este README foi melhorado e estilizado usando IA.
