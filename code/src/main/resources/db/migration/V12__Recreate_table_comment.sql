DROP TABLE Comment;

CREATE TABLE Comment (
     id INT AUTO_INCREMENT NOT NULL,
     user_pk INT NOT NULL,
     barrier_scenario_fk INT NOT NULL,
     content TINYTEXT NOT NULL,
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     CONSTRAINT FOREIGN KEY (user_pk) REFERENCES User(id) on DELETE CASCADE,
     CONSTRAINT FOREIGN KEY (barrier_scenario_fk) REFERENCES BarrierScenario(id) on DELETE CASCADE,
     PRIMARY KEY (id)
);