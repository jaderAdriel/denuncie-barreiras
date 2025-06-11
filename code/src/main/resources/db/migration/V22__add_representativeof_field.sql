ALTER TABLE Moderator
    ADD COLUMN entity_fk CHAR(14),
    ADD CONSTRAINT FOREIGN KEY Moderator(entity_fk) REFERENCES Entity(cnpj);