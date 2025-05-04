CREATE TABLE IF NOT EXISTS Person (
    id INT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    birth_date DATE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS User (
    person_id INT,
    login VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    CONSTRAINT FOREIGN KEY (person_id) REFERENCES Person(id) ON DELETE CASCADE,
    PRIMARY KEY (person_id)
);

CREATE TABLE IF NOT EXISTS SchoolStaff (
    user_id INT,
    cellphone VARCHAR(50) NOT NULL,
    CONSTRAINT FOREIGN KEY (user_id) REFERENCES User(person_id) ON DELETE CASCADE,
    PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS Student (
    user_id INT,
    enrollment_code VARCHAR(100) NOT NULL,
    entry_year YEAR NOT NULL,
    CONSTRAINT FOREIGN KEY (user_id) REFERENCES User(person_id) ON DELETE CASCADE,
    PRIMARY KEY (user_id)
);