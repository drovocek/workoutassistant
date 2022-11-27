CREATE TABLE IF NOT EXISTS exercise (
    id UUID PRIMARY KEY,
    title VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(250) NOT NULL,
    complexity SMALLINT NOT NULL CONSTRAINT complexity_boarders CHECK (complexity BETWEEN 1 AND 10)
);

