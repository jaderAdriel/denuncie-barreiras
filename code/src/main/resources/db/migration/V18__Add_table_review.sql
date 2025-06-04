CREATE TABLE Review(
    report_fk INT NOT NULL,
    author_fk INT NOT NULL,
    comment TINYTEXT,
    is_valid boolean NOT NULL ,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FOREIGN KEY (report_fk) REFERENCES Report(id) ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (author_fk) REFERENCES Moderator(user_fk) ON DELETE NO ACTION
);