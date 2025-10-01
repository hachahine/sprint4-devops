# 🛠️ Mottu Challenge API

Este projeto é uma API desenvolvida como parte de um desafio proposto pela Mottu. A aplicação foi construída em **Java com Spring Boot**, e tem como objetivo gerenciar dispositivos, funcionários, motocicletas, unidades e pátios da empresa de forma eficiente e organizada.

## 👨‍💻 Equipe

- **Guilherme Cardoso** – RM555178  
- **João Pedro Motta** – RM556557  
- **Hassan Chahine** – RM556715

## 🚀 Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring Security
- Maven
- JPA/Hibernate
- H2 Database (para testes)
- Oracle SQL (para prod)
- Flyway
- Thymeleaf
- Lombok

## 📁 Estrutura do Projeto

O projeto está dividido nas seguintes camadas:

- `controller`: Camada responsável pelos endpoints da API.
- `domain`: Contém todas as regras de negócio e entidades da aplicação.
   - `dto`: Objetos de transferência de dados entre camadas.
   - `entity`: Entidades que representam as tabelas do banco de dados.
   - `repository`: Interfaces responsáveis pela persistência de dados.
   - `service`: Camada onde se concentra a lógica de negócio da aplicação.
- `infra`: Camada de infraestrutura e configurações transversais.

## ▶️ Como Executar o Projeto

### Pré-requisitos

- Java 21 instalado
- Maven instalado

### Passos para rodar localmente

1. Clone este repositório:

   ```bash
   git clone https://github.com/Cardosodev1/mottu.challange-api.git
   cd mottu.challange-api

2. Compile e execute o projeto com Maven:

   ```bash
   mvnw.cmd spring-boot:run

3. Acesse a aplicação no navegador:
   - API em: `http://localhost:8080`
