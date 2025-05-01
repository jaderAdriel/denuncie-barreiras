CREATE TABLE IF NOT EXISTS BarrierScenario(
    id INT AUTO_INCREMENT,
    type VARCHAR(45),
    author_fk INT,
    content TEXT,
    title VARCHAR(45),
    creation_date DATE,
    likes INT,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS BarrierScenario_Like(
       user_fk INT,
       scenario_fk INT,
       CONSTRAINT FOREIGN KEY (user_fk) REFERENCES User(id) ON DELETE CASCADE,
       CONSTRAINT FOREIGN KEY (scenario_fk) REFERENCES BarrierScenario(id) ON DELETE CASCADE,
       PRIMARY KEY (scenario_fk)
);