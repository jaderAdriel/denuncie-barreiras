CREATE TABLE IF NOT EXISTS Law(
    code VARCHAR(100),
    date DATE NOT NULL,
    officialLink TINYTEXT,
    title VARCHAR(255) NOT NULL,
    description TINYTEXT NOT NULL,
    PRIMARY KEY (code)
);

ALTER TABLE Comment
    ADD response_to_fk INT,
    ADD content TINYTEXT NOT NULL,
    ADD creation_datetime TIMESTAMP DEFAULT NOW(),
    ADD CONSTRAINT FOREIGN KEY (response_to_fk) REFERENCES Comment(id);