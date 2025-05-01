CREATE TABLE IF NOT EXISTS User (
    id INT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    birth_date DATE,
    password VARCHAR(50) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Moderator (
    user_fk INT,
    cellphone VARCHAR(50) NOT NULL,
    CONSTRAINT FOREIGN KEY (user_fk) REFERENCES User(id) ON DELETE CASCADE,
    PRIMARY KEY (user_fk)
);

