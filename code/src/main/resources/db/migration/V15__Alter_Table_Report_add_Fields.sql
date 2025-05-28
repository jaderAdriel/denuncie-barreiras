ALTER TABLE Report
    ADD COLUMN review_comment TEXT,
    ADD COLUMN review_author_fk INT,
    ADD COLUMN review_create_at DATE,
    ADD COLUMN review_is_valid BOOLEAN;

ALTER TABLE Report
    ADD CONSTRAINT fk_review_author
        FOREIGN KEY (review_author_fk) REFERENCES Moderator(user_fk) ON DELETE CASCADE;