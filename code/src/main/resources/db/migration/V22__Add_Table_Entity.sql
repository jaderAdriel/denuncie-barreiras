CREATE TABLE IF NOT EXISTS Entity(
    cnpj CHAR(14),
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    create_at DATE,
    type VARCHAR(50),
    city VARCHAR(50),
    street VARCHAR(50),
    state VARCHAR(50),
    postal_code VARCHAR(50),
    PRIMARY KEY(cnpj)
);