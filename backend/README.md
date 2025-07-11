# Projeto Econômetro - Backend Web

## Visão Geral

O **Econometro** é um sistema de backend para comparação de preços e alertas de produtos.

## Tecnologias Principais

- Spring Boot
- Spring Security
- JWT
- JPA/Hibernate
- SQLite

## Como Rodar o Projeto

1. **Pré-requisitos:**
   - Java Development Kit (JDK) 17 ou superior
   - Maven 3.x

2. **Configuração do Banco de Dados:**
   O projeto utiliza SQLite. O banco de dados será criado automaticamente ao iniciar a aplicação se não existir. Certifique-se de que as dependências do SQLite estejam configuradas no `pom.xml`.

3. **Build e Execução:**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

   A aplicação estará acessível em `http://localhost:8080`.
   A documentação da API (Swagger UI) pode ser encontrada em `http://localhost:8080/swagger-ui.html`.

