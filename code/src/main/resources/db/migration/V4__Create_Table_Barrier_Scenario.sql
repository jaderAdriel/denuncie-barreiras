CREATE TABLE IF NOT EXISTS Like(
    user_id_fk INT,
    scenario_id_fk INT,
    CONSTRAINT FOREIGN KEY (user_id_fk) REFERENCES User(id) ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (scenario_id_fk) REFERENCES Barrier_scenario(id) ON DELETE CASCADE,
    PRIMARY KEY (scenario_id_fk)
);

CREATE TABLE IF NOT EXISTS Barrier_scenario(
    id INT AUTO_INCREMENT,
    type VARCHAR(45),
    author MODERATOR,
    content TEXT,
    title VARCHAR(45),
    creation_date DATE,
    likes LIKE,
    PRIMARY KEY (id)
);