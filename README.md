## Sprint 4 - DevOps Tools & Cloud Computing 
Esta é uma API REST desenvolvida em Java com Spring Boot para o gerenciamento de ativos da Mottu. 
A aplicação permite o cadastro e controle de unidades operacionais, pátios, funcionários, motocicletas e dispositivos de rastreamento associados. 

# Autentica o Docker no seu Registro de Contêiner (ACR)
az acr login --name acrmottuchallengerm556715
Passo 3: Criar a Infraestrutura na AzureOs comandos abaixo criam o Grupo de Recursos e o Banco de Dados PostgreSQL.# 1. Criar o Grupo de Recursos
az group create --name rg-mottu-challenge --location brazilsouth

# 2. Criar o servidor PostgreSQL
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

# 3. Criar o banco de dados dentro do servidor
az postgres flexible-server db create \
  --resource-group rg-mottu-challenge \
  --server-name pg-mottu-challenge-rm556715 \
  --database-name mottu_db
Passo 4: Construir e Enviar a Imagem Docker# 1. Construir a imagem Docker da aplicação (versão v5)
docker build -t acrmottuchallengerm556715.azurecr.io/mottu-api:v5 .

# 2. Enviar a imagem para o Azure Container Registry (ACR)
docker push acrmottuchallengerm556715.azurecr.io/mottu-api:v5
Passo 5: Executar a Aplicação no ACIEste comando inicia o contêiner na nuvem, injetando as variáveis de ambiente necessárias para conectar ao banco de dados.# Obtenha a senha do seu ACR (se necessário)
az acr credential show --name acrmottuchallengerm556715

# Crie e execute o contêiner
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
Após o comando ser concluído, aguarde 1-2 minutos para a aplicação iniciar. A URL base da API será: http://api-mottu-rm556715.brazilsouth.azurecontainer.io:80807. Testando a Aplicação (Exemplos de CRUD)Use uma ferramenta como o Postman para testar os endpoints.Passo 1: Obter Token de AutenticaçãoMétodo: POSTURL: http://api-mottu-rm556715.brazilsouth.azurecontainer.io:8080/loginBody (JSON):{
    "login": "admin",
    "password": "123456"
}
Resultado: Copie o token JWT retornado para usar nas próximas requisições.Passo 2: CRUD de Unidades (/units)Adicione o Bearer Token no Header de Authorization em todas as requisições abaixo.Criar uma Unidade (POST)Método: POSTURL: ...:8080/unitsBody (JSON):{
    "name": "Mottu - Filial São Paulo",
    "address": "Avenida Paulista, 1000",
    "neighborhood": "Bela Vista"
}
Listar Unidades (GET)Método: GETURL: ...:8080/unitsAtualizar uma Unidade (PUT)Método: PUTURL: ...:8080/units/1 (use o ID da unidade que você criou)Body (JSON):{
    "name": "Mottu - Sede SP (Matriz)",
    "address": "Avenida Paulista, 1001"
}
Deletar uma Unidade (DELETE)Método: DELETEURL: ...:8080/units/2 (use o ID de outra unidade que você criou)
