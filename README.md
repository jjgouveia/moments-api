# Moments API

# 🔨 Desenvolvimento

API de uma rede social contruída em Java com Spring Boot, utilizando o banco de dados MongoDB e algumas dependências
como o Lombok,
Swagger, JWT, entre outras.

# 📦 Execução

A aplicação está disponível no Docker Hub, para executá-la basta executar o seguinte comando na raiz do projeto:

```shell
docker compose up -d
```

# 📝 Documentação

A documentação da API está disponível no Swagger, para acessá-la basta acessar o seguinte link após executar a
aplicação:

http://localhost:8080/swagger-ui/index.html

# Localstack

Para conseguir upar imagens corretamente no localstack, é necessário executar o seguinte comando:

```shell
docker exec -it localstack bash
```

E dentro do bash do localstack, executar o seguinte comando:

```shell
aws configure --profile default
```

E setar as seguintes configurações:

```shell
AWS Access Key ID [None]: moments
AWS Secret Access Key [None]: moments
Default region name [None]: us-east-2
Default output format [None]: json
```

Após isso, é necessário executar o seguinte comando para criar o bucket:

```shell
aws --endpoint-url=http://localhost:4566 s3 mb s3://moments
```

# 📍 Author

Jarbas Gouveia.
