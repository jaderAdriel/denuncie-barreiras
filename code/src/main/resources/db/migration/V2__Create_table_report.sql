CREATE TABLE IF NOT EXISTS Report(
    id INT AUTO_INCREMENT,
    reporter_fk INT,
    type VARCHAR(45) NOT NULL,
    ambient VARCHAR(45) NOT NULL,
    severity TINYINT NOT NULL,
    anonymous_report BOOLEAN NOT NULL,
    event_detailing TEXT NOT NULL,
    related_scenario_fk INT,
    CONSTRAINT FOREIGN KEY (reporter) REFERENCES User(id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);