## Sprint 4 - DevOps Tools & Cloud Computing 
Esta é uma API REST desenvolvida em Java com Spring Boot para o gerenciamento de ativos da Mottu. 
A aplicação permite o cadastro e controle de unidades operacionais, pátios, funcionários, motocicletas e dispositivos de rastreamento associados. 

Integrantes:
- RM555178 - Guilherme Cardoso dos Santos
- RM556715 - Hassan Chahine
- RM556557 - João Pedro Motta Marcolini

## Tecnologias Utilizadas
**Backend**: Java 21, Spring Boot 3.2.5 (Web, Data JPA, Security)

**Banco de Dados**: PostgreSQL (PaaS na Azure)

**Migração de Schema**: Flyway

**Cloud**: Microsoft Azure (ACI, ACR, Azure Database for PostgreSQL)

**Containerização**: Docker

## Pré-requisitos para o Deploy
Antes de começar, garanta que você tem as seguintes ferramentas instaladas e configuradas:
- Git
- Azure CLI
- Docker Desktop

## Passo a Passo do Deploy
Siga os passos abaixo para implantar a aplicação na nuvem Azure.

**Passo 1:** Clonar o Repositório
```
git clone https://github.com/hachahine/sprint4-devops.git
cd sprint4-devops
```

**Passo 2:** Login na Azure CLI e no Docker
```
// Autentica na sua conta da Azure (abrirá o navegador)
az login

// Autentica o Docker no seu Registro de Contêiner (ACR)
az acr login --name acrmottuchallengerm556715
```

**Passo 3:** Criar a Infraestrutura na Azure
Os comandos abaixo criam o Grupo de Recursos e o Banco de Dados PostgreSQL.

1. Criar o Grupo de Recursos  
`az group create --name rg-mottu-challenge --location brazilsouth`

2. Criar o servidor PostgreSQL
```
az postgres flexible-server create \
  --name pg-mottu-challenge-rm556715 \
  --resource-group rg-mottu-challenge \
  --location brazilsouth \
  --admin-user adminmottu \
  --admin-password 'SUA_SENHA_DO_BANCO_AQUI' \
  --sku-name Standard_B1ms \
  --tier Burstable \
  --version 14 \
  --storage-size 32 \
  --public-access 0.0.0.0
```

3. Criar o banco de dados dentro do servidor
```
az postgres flexible-server db create \
  --resource-group rg-mottu-challenge \
  --server-name pg-mottu-challenge-rm556715 \
  --database-name mottu_db
```

**Passo 4:** Construir e Enviar a Imagem Docker
1. Construir a imagem Docker da aplicação (versão v5)  
`docker build -t acrmottuchallengerm556715.azurecr.io/mottu-api:v5 .`

2. Enviar a imagem para o Azure Container Registry (ACR)  
`docker push acrmottuchallengerm556715.azurecr.io/mottu-api:v5`

**Passo 5:** Executar a Aplicação no ACI
Este comando inicia o contêiner na nuvem, injetando as variáveis de ambiente necessárias para conectar ao banco de dados.

Crie e execute o contêiner
```
az container create \
  --resource-group rg-mottu-challenge \
  --name mottu-api-container \
  --image acrmottuchallengerm556715.azurecr.io/mottu-api:v5 \
  --registry-username acrmottuchallengerm556715 \
  --registry-password 'SENHA_DO_ACR_AQUI' \
  --ports 8080 \
  --os-type Linux \
  --cpu 1 \
  --memory 2 \
  --dns-name-label api-mottu-rm556715 \
  --environment-variables \
    'DB_URL'='jdbc:postgresql://[pg-mottu-challenge-rm556715.postgres.database.azure.com:5432/mottu_db?sslmode=require](https://pg-mottu-challenge-rm556715.postgres.database.azure.com:5432/mottu_db?sslmode=require)' \
    'DB_USER'='adminmottu' \
    'DB_PASS'='SUA_SENHA_DO_BANCO_AQUI' \
    'JWT_SECRET'='jwtsecret'
```

## Testes
Use uma ferramenta como o Postman para testar os endpoints.

**Passo 1:** Obter Token de Autenticação  
Método: **POST**

**URL:** http://api-mottu-rm556715.brazilsouth.azurecontainer.io:8080/login

Body (JSON):  
```
{
    "login": "admin",
    "password": "123456"
}
```  
**Resultado**: Copie o token JWT retornado para usar nas próximas requisições.

**Passo 2:** CRUD de Unidades (/units)
Adicione o Bearer Token no Header de Authorization em todas as requisições abaixo.

- POST
  
URL: http://api-mottu-rm556715.brazilsouth.azurecontainer.io:8080/units

Body (JSON):
```
{
    "name": "Mottu - Filial São Paulo",
    "address": "Avenida Paulista, 1000",
    "neighborhood": "Bela Vista"
}
```  
**Resposta**:
```
{
    "id": 7,
    "name": "Mottu - Filial São Paulo",
    "address": "Avenida Paulista, 1000",
    "neighborhood": "Bela Vista"
}
```

---

- GET
URL: http://api-mottu-rm556715.brazilsouth.azurecontainer.io:8080/units

**Resposta**:
```
{
    "content": [
        {
            "id": 6,
            "name": "Mottu - Filial Rio de Janeiro",
            "address": "Avenida Atlântica, 2000",
            "neighborhood": "Copacabana"
        },
        {
            "id": 7,
            "name": "Mottu - Filial São Paulo",
            "address": "Avenida Paulista, 1000",
            "neighborhood": "Bela Vista"
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 10,
        "sort": {
            "unsorted": false,
            "sorted": true,
            "empty": false
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "totalPages": 1,
    "totalElements": 2,
    "last": true,
    "numberOfElements": 2,
    "first": true,
    "size": 10,
    "number": 0,
    "sort": {
        "unsorted": false,
        "sorted": true,
        "empty": false
    },
    "empty": false
}
```

---

- PUT

URL: http://api-mottu-rm556715.brazilsouth.azurecontainer.io:8080/units/{id}

Body (JSON):
```
{
    "name": "Mottu - Sede SP (Matriz)",
    "address": "Avenida Paulista, 1001"
}
```

---

- DELETE

URL: http://api-mottu-rm556715.brazilsouth.azurecontainer.io:8080/units/{id}

**Resposta:**  
`"No Content" - 204.`

