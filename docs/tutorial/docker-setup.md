## Visão Geral do Ambiente com Docker

### O que este ambiente faz?

Este projeto utiliza **Docker** e **Docker Compose** para criar um ambiente de desenvolvimento completo e padronizado, garantindo que todos os desenvolvedores da equipe possam executar o sistema da mesma forma, independentemente do sistema operacional.

O comando `docker-compose up -d --build` irá:

1. Criar e iniciar os containers definidos no arquivo `docker-compose.yml`.
2. Instanciar os serviços necessários: banco de dados MySQL, ferramenta de migrations (Flyway) e a aplicação principal.
3. Garantir que a base de dados seja criada e populada corretamente antes da aplicação iniciar, graças à configuração de dependências e healthchecks.

### O que é Docker?

[Docker](https://www.docker.com/) é uma plataforma que permite empacotar uma aplicação e todas as suas dependências em um container. Isso facilita o desenvolvimento, teste e implantação de aplicações de forma reprodutível e isolada.

**Docker Compose** é uma ferramenta que permite definir e gerenciar múltiplos containers com um único arquivo (`docker-compose.yml`), ideal para arquiteturas baseadas em múltiplos serviços.

---

## Containers que serão instanciados

O `docker-compose.yml` define três serviços principais:

### 1. `db` (MySQL 8)
- Container do banco de dados MySQL.
- Inicializa com variáveis definidas no `.env`.
- Expõe a porta `3306` para acesso local.
- Executa o script `init.sql` ao iniciar (caso exista).
- Armazena dados em volume persistente `mysql_data`.
- Possui um *healthcheck* para garantir que o banco esteja pronto antes que os outros serviços dependam dele.

### 2. `flyway` (Ferramenta de Migrations)
- Executa as migrations do banco de dados automaticamente após o container do MySQL estar saudável.
- Utiliza os scripts localizados em `src/main/resources/db/migration`.
- Após completar com sucesso, permite que o próximo serviço (`app`) inicie.

### 3. `app` (Aplicação principal)
- Constrói a aplicação a partir do Dockerfile no diretório atual (`.`).
- Depende do Flyway e só será iniciada após as migrations serem aplicadas com sucesso.
- Exposta na porta `8000` do host (acessível via http://localhost:8000).
- Recebe as variáveis de ambiente para se conectar ao banco de dados.


## Pré-requisitos

**Atenção:** Certifique-se de que o Docker e Docker Compose estão instalados em seu sistema antes de prosseguir. Você pode verificar com:

```bash
docker --version
docker-compose --version  # ou 'docker compose version' para versões mais novas
```

Caso não estejam instalados, siga as instruções na [documentação oficial do Docker](https://docs.docker.com/get-docker/).

## Configuração Inicial

1. **Navegue até a raiz do projeto e entre em code**
    - Certifique-se de estar no diretório onde o arquivo `docker-compose.yml` está localizado

2. **Crie o arquivo .env**
    - No diretório raiz, crie um arquivo chamado `.env` com o seguinte conteúdo:

```env
# Configurações do Banco de Dados MySQL
DB_URL=jdbc:mysql://db:3306/barreirasAppDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
DB_USER=developer
DB_PASSWORD=developer123

# Configurações do Container MySQL
MYSQL_ROOT_PASSWORD=root123*
MYSQL_DATABASE=barreirasAppDB
MYSQL_USER=developer
MYSQL_PASSWORD=developer123
```

## Iniciando os Containers

Execute o comando abaixo para iniciar os containers:

```bash
docker-compose up -d --build
```
ou (para versões mais novas do Docker)
```bash
docker compose up -d --build
```

**Nota:** A flag `--build` garante que as imagens serão reconstruídas caso haja alterações.

## Comandos Úteis

- Verificar o status dos containers:
  ```bash
  docker-compose ps
  ```

- Visualizar logs da aplicação:
  ```bash
  docker-compose logs -f app
  ```

- Parar os containers:
  ```bash
  docker-compose down
  ```

- Parar e remover volumes (dados persistentes):
  ```bash
  docker-compose down -v
  ```

## Acessando o Banco de Dados

Para acessar o MySQL diretamente:

```bash
docker exec -it barreirasapp-db mysql -u developer -p barreirasAppDB
```
(Quando solicitado, insira a senha: `123`)

## Gerenciamento de Migrations

Consulte nosso guia específico para migrations:
- [Flyway Migrations Guide](docs/migrations-flyway.md)
- [Liquibase Migrations Guide](docs/migrations-liquibase.md)

