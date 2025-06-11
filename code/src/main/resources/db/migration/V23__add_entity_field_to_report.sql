ALTER TABLE Report
    ADD COLUMN entity_cnpj CHAR(14),
    ADD CONSTRAINT FOREIGN KEY Report(entity_cnpj) REFERENCES Entity(cnpj);