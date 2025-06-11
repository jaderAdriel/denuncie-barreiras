## Acessa o banco de dados
 - docker exec -it code-db-1 mysql -u root -p

## iniciar o container flyway e rodar o repair
- docker start code-flyway-1
- docker exec -it code-flyway-1 flyway repair
  docker compose run flyway baseline -baselineVersion=1 -baselineDescription="Baseline"
