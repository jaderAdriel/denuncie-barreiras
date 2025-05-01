

# Guia de Migrations com Flyway



## 🧩 O que são Migrations?

**Migrations** (ou migrações) são **scripts versionados** que descrevem passo a passo as alterações feitas no banco de dados, como:

- Criação de tabelas
- Adição de colunas
- Alteração de tipos de dados
- Inclusão de constraints (chaves primárias, estrangeiras, etc.)
- Inserção de dados iniciais

Elas permitem que **o banco de dados evolua junto com a aplicação**, garantindo que todos os ambientes (desenvolvimento, teste, produção) estejam com a mesma estrutura de dados.

---

## 🎯 Para que servem?

- ✅ **Controle de versão do banco de dados** — como um Git para o seu schema.
- ✅ **Facilita o trabalho em equipe** — cada mudança no banco fica registrada e é aplicada na ordem correta.
- ✅ **Evita erros manuais** — ninguém precisa rodar comandos SQL na mão.
- ✅ **Automatiza o processo** — ao iniciar o sistema, o banco já é criado/configurado automaticamente.
- ✅ **Segurança e rastreabilidade** — você sabe quem criou cada alteração e quando.

---

## 📏 Regras Importantes ao Usar Migrations

1. **Use nomes de arquivos claros e descritivos**
    - Formato padrão do Flyway:
      ```
      V<versão>__<descricao>.sql
      Ex: V1__criar_tabela_usuarios.sql
      ```

2. **Nunca edite uma migration que já foi aplicada**
    - Isso pode quebrar o banco dos outros devs ou da produção.
    - Se quiser corrigir algo, crie uma nova migration.

3. **Mantenha a ordem das versões**
    - O Flyway aplica os scripts em ordem crescente (V1 → V2 → V3...).
    - Versões duplicadas ou fora de ordem podem causar conflitos.

4. **Evite dados sensíveis ou específicos de um ambiente**
    - Exemplo: inserir um usuário admin com senha em texto plano pode ser um problema em produção.

5. **Teste localmente antes de subir para o repositório**
    - Verifique se a migration executa corretamente em um banco limpo.

6. **Use `baseline` apenas se for começar a usar migrations em um banco já existente**
    - Isso define a partir de qual versão o Flyway deve considerar que o banco está.

7. **Evite migrations muito grandes**
    - Prefira dividir por funcionalidades para facilitar leitura, revisão e rollback (caso precise).

---

Se quiser, posso complementar com **exemplos de boas práticas** ou um **modelo inicial de migration com criação de tabela e inserção de dados**. Deseja?

Este projeto utiliza o [Flyway](https://flywaydb.org/) como ferramenta de versionamento de banco de dados. O Flyway aplica migrations automaticamente ao iniciar o ambiente via Docker Compose.

## 📂 Estrutura das Migrations

As migrations devem ser armazenadas no seguinte diretório:

```
src/main/resources/db/migration/
```

Cada arquivo de migration deve seguir a convenção de nomenclatura do Flyway:

```
V<versão>__<descrição>.sql
```

Exemplo:

```
V1__criar_tabela_usuarios.sql
V2__inserir_dados_iniciais.sql
```

> ✅ O `V` deve ser seguido por um número de versão (inteiro ou decimal). As versões são aplicadas em ordem crescente.
>
> ✅ Use `__` (dois underlines) para separar a versão da descrição.
>
> ⚠️ A descrição não pode conter espaços. Use `_` ou `-`.

---

## ▶️ Aplicando Migrations

As migrations são aplicadas automaticamente toda vez que você executa o comando:

```bash
docker-compose up -d --build
```

Esse comando irá:
1. Subir o container `db` (MySQL).
2. Esperar o banco de dados estar saudável.
3. Rodar o container `flyway`, que aplica todas as migrations pendentes.
4. Só então iniciar a aplicação no container `app`.

---

## ✅ Verificando se as Migrations foram Aplicadas

Você pode verificar no banco de dados acessando a tabela criada automaticamente pelo Flyway chamada `flyway_schema_history`.

```sql
SELECT * FROM flyway_schema_history;
```

Ou via terminal:

```bash
docker exec -it barreirasapp-db mysql -u developer -p barreirasAppDB
```

Senha: `developer123`

---

## 🔄 Adicionando Novas Migrations

1. Crie um novo arquivo `.sql` no diretório `src/main/resources/db/migration/`.
2. Nomeie seguindo o padrão `V<N>__<descricao>.sql`.
3. Adicione os comandos SQL que deseja executar.

Exemplo:

```sql
-- src/main/resources/db/migration/V3__adicionar_coluna_email.sql
ALTER TABLE usuarios ADD COLUMN email VARCHAR(100);
```

4. Rebuild os containers:

```bash
docker-compose up -d --build
```

---

## 🧹 Reaplicando Migrations (ambiente de desenvolvimento)

Se precisar **recomeçar do zero** (apenas para desenvolvimento):

```bash
docker-compose down -v  # remove containers e volumes
docker-compose up -d --build
```
⚠️ **Cuidado!** Esse comando apaga todos os dados do banco de dados (volume).

---
## Conserte as migrations sem excluir tudo

```bash
sudo docker compose run flyway repair
```



## 📄 Referências

- [Documentação oficial do Flyway](https://flywaydb.org/documentation/)
- [Exemplos de Migrations](https://flywaydb.org/documentation/concepts/migrations)
