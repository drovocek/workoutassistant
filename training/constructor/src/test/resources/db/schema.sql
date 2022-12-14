DROP TABLE IF EXISTS workout_result;
DROP TABLE IF EXISTS workout_session;
DROP TABLE IF EXISTS workout_plan;
DROP TABLE IF EXISTS workout_round;
DROP TABLE IF EXISTS exercise;

CREATE TABLE exercise (
    id UUID PRIMARY KEY,
    title VARCHAR(50) NOT NULL CHECK (title <> '') UNIQUE,
    description VARCHAR(250),
    complexity SMALLINT NOT NULL CONSTRAINT complexity_boarders CHECK (complexity BETWEEN 1 AND 10)
);

CREATE TABLE workout_round (
    id UUID PRIMARY KEY,
    round_schema JSONB NOT NULL,
    title VARCHAR(50) NOT NULL CHECK (title <> '') UNIQUE,
    description VARCHAR(250) NOT NULL
);

CREATE TABLE workout_plan(
    id UUID PRIMARY KEY,
    workout_schema JSONB NOT NULL,
    title VARCHAR(50) NOT NULL CHECK (title <> '') UNIQUE,
    description VARCHAR(250)
);

CREATE TABLE workout_session(
    id UUID PRIMARY KEY,
    workout_plan JSONB NOT NULL,
    date_time TIMESTAMP NOT NULL,
    note VARCHAR(250)
);

CREATE TABLE workout_result(
    id UUID PRIMARY KEY,
    workout_session_id UUID REFERENCES workout_session (id) ON DELETE CASCADE UNIQUE,
    workout_schema JSONB NOT NULL,
    title VARCHAR(50) NOT NULL CHECK (title <> '') UNIQUE,
    description VARCHAR(250)
);

