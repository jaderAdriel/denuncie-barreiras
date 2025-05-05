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


# denuncie-barreiras
Sistema de Denúncia e Monitoramento de Barreiras Atitudinais

Acesse [Guia de Ambiente de Desenvolvimento](docs/tutorial/readme.md)

---

## Visão Geral

Este repositório contém um sistema para o recebimento e monitoramento de denúncias sobre barreiras atitudinais, com foco na acessibilidade e nos direitos das pessoas com deficiência no ambiente escolar.

O que são barreiras atitudinais?
São comportamentos ou atitudes negativas, como discriminação, bullying ou exclusão, que dificultam a participação plena de pessoas com deficiência na vida escolar e social.

A plataforma permite que alunos, professores e membros da comunidade escolar registrem denúncias de forma anônima, categorizadas por tipo, local e gravidade. As ocorrências são encaminhadas para a gestão escolar, que pode acompanhar o status, registrar pareceres e adotar medidas. Os usuários recebem notificações sobre o andamento dos casos e têm acesso a um painel com o histórico de suas denúncias.

---

## Requisitos Funcionais
RF01 - Cadastro de Usuário:
O sistema deve permitir o cadastro de diferentes tipos de usuários, com opção de email ou redes sociais.

RF02 - Login e Autenticação:
Sistema com recuperação de senha e autenticação em duas etapas.

RF03 - Registro de Denúncias:
Permite registro com a opção de anonimato.

RF04 - Classificação da Denúncia:
Classificação por tipo, local e gravidade. Supervisores podem reclassificar.

RF05 - Encaminhamento para Gestão Escolar:
Encaminha denúncias para análise da gestão escolar.

RF06 - Acompanhamento de Status:
Usuários podem acompanhar o andamento da denúncia.

RF07 - Inserção de Pareceres:
A gestão pode registrar pareceres e ações tomadas.

RF08 - Notificações:
O sistema notifica os usuários sobre atualizações.

RF09 - Histórico de Denúncias:
Exibe painel com histórico do usuário.

RF10 - Controle de Acesso por Perfil:
Funcionalidades restritas conforme tipo de usuário.

---

 ## Requisitos Não Funcionais
RNF01 - Segurança e Privacidade:
Proteção de dados e anonimato com criptografia.

RNF02 - Usabilidade:
Interface acessível, intuitiva e inclusiva.

RNF03 - Desempenho:
Resposta em até 2 segundos.

RNF04 - Escalabilidade:
Suporte ao crescimento de usuários e registros.

RNF05 - Compatibilidade:
Funciona em navegadores e dispositivos móveis.

RNF06 - Registro de Atividades:
Log de ações para monitoramento e auditoria.

---

## Tecnologias Utilizadas

* [Docker](https://www.docker.com/)
* [Docker Compose](https://docs.docker.com/compose/)
* [MySQL 8](https://www.mysql.com/)
* [Flyway](https://flywaydb.org/)
* [Java](https://docs.oracle.com/en/java/javase/17/)

---

## Repositório Online
Acesse o repositório completo no GitHub:
https://github.com/jaderAdriel/denuncie-barreiras

---

## Início Rápido

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/jaderAdriel/denuncie-barreiras.git
   cd denuncie-barreiras
   ```

---

## Licença

Este projeto está licenciado sob os termos da [Licença MIT](LICENSE).

---

## Autor

Projeto realizado como atividade avaliativa da disciplina **Linguagem de Programação Orientada a Objetos**. Desenvolvido por **Ana Rode Rodrigues dos Santos, Jader Adriel Miranda Souza, Samara Mercês Pereira e Sávio Kauan Silveira Lopes**, sob orientação do professor **Woquiton Lima Fernandes**.  


---

## Documentação Complementar

* [Guia de Migrations (Flyway)](docs/migrations-guide.md)
* [Guia do Ambiente com Docker](docs/docker-setup.md)
* [Tutorial Inicial](docs/tutorial/readme.md)

---

## Contribuição

Contribuições são bem-vindas! Siga estas etapas:

1. Fork este repositório
2. Crie um branch com sua feature: `git checkout -b minha-feature`
3. Commit suas alterações: `git commit -m 'feat: nova funcionalidade'`
4. Push para o branch: `git push origin minha-feature`
5. Crie um Pull Request


