DROP TABLE IF EXISTS exercise;
DROP TABLE IF EXISTS workout_round;

CREATE TABLE exercise (
    id UUID PRIMARY KEY,
    title VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(250) NOT NULL,
    complexity SMALLINT NOT NULL CONSTRAINT complexity_boarders CHECK (complexity BETWEEN 1 AND 10)
);

CREATE TABLE workout_round (
    id UUID PRIMARY KEY,
    round_schema JSONB NOT NULL,
    title VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(250) NOT NULL,
    complexity SMALLINT NOT NULL CONSTRAINT complexity_boarders CHECK (complexity BETWEEN 1 AND 10)
);


