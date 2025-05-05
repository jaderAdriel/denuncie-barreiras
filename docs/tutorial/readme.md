# 🛠️ Guia de Ambiente de Desenvolvimento

Este repositório contém os recursos necessários para configurar rapidamente um ambiente de desenvolvimento completo utilizando **Docker** e **migrations com Flyway**. A proposta é garantir que todos os desenvolvedores possam trabalhar com a mesma estrutura de forma padronizada, segura e automatizada.

---

## 📦 1. Configurando o Docker

O Docker é utilizado neste projeto para isolar e gerenciar os serviços da aplicação, como o banco de dados MySQL e o servidor da aplicação.

➡️ Acesse o guia completo de configuração do ambiente Docker:  
📄 [`docs/docker-setup.md`](./docker-setup.md)

Neste guia, você vai aprender:

- Como instalar e verificar o Docker
- Como criar o arquivo `.env`
- Como iniciar os containers
- Comandos úteis para monitorar ou remover os serviços
- Como acessar diretamente o banco de dados MySQL em container

---

## 🗃️ 2. Utilizando Migrations com Flyway

O Flyway é utilizado para versionar as alterações no banco de dados. Ele aplica automaticamente os scripts de criação e alteração de schema sempre que o ambiente é iniciado.

➡️ Veja o guia completo sobre como usar o Flyway para gerenciar migrations:  
📄 [`docs/migrations-guide.md`](./migrations-guide.md)

Lá você encontrará:

- O que são migrations e por que usá-las
- Estrutura e nomenclatura correta dos arquivos
- Como adicionar novas migrations
- Como reaplicar do zero (em ambiente de desenvolvimento)
- Regras importantes para manter o histórico consistente

---

## 📁 Estrutura de Pastas Relevante

```
project-root/
├── docker-compose.yml
├── .env
├── src/
│   └── main/
│       └── resources/
│           └── db/
│               └── migration/   # Migrations do Flyway (ex: V1__criar_tabela.sql)
├── docs/
│   ├── docker-setup.md          # Guia de configuração Docker
│   └── migrations-guide.md      # Guia de migrations com Flyway
└── init.sql                     # Script inicial para criar o schema, se necessário
```

---

## 🚀 Início Rápido

1. Configure seu ambiente seguindo o [guia do Docker](docs/docker-setup.md)
2. Crie suas migrations seguindo o [guia do Flyway](docs/migrations-guide.md)
3. Inicie o ambiente:

```bash
docker-compose up -d --build
```
