CREATE TABLE IF NOT EXISTS REPORT(
    id INT AUTO_INCREMENT,
    user_id INT,
    type VARCHAR(45) NOT NULL,
    ambient VARCHAR(45) NOT NULL,
    address VARCHAR(45),
    severity TINYINT NOT NULL,
    anonymous_report BOOLEAN NOT NULL,
    event_detailing TEXT NOT NULL,
    related_scenario VARCHAR(45),
    CONSTRAINT FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);