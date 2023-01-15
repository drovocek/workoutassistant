DROP TABLE IF EXISTS training_session;
DROP TABLE IF EXISTS workout_plan;
DROP TABLE IF EXISTS round;
DROP TABLE IF EXISTS exercise;

CREATE TABLE exercise (
    id UUID PRIMARY KEY,
    title VARCHAR(50) NOT NULL CHECK (title <> '') UNIQUE,
    description VARCHAR(250),
    complexity SMALLINT NOT NULL CONSTRAINT complexity_boarders CHECK (complexity BETWEEN 1 AND 5)
);

CREATE TABLE round (
    id UUID PRIMARY KEY,
    stations_schema JSONB NOT NULL,
    title VARCHAR(50) NOT NULL CHECK (title <> '') UNIQUE,
    description VARCHAR(250) NOT NULL
);

CREATE TABLE workout(
    id UUID PRIMARY KEY,
    rounds_schema JSONB NOT NULL,
    title VARCHAR(50) NOT NULL CHECK (title <> '') UNIQUE,
    description VARCHAR(250)
);

CREATE TABLE training_session(
    id UUID PRIMARY KEY,
    workout_plan JSONB NOT NULL,
    date_time TIMESTAMP NOT NULL,
    note VARCHAR(250)
);

