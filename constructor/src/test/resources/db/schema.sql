DROP TABLE IF EXISTS workout;
DROP TABLE IF EXISTS round;
DROP TABLE IF EXISTS exercise;

CREATE TABLE exercise
(
    id    UUID PRIMARY KEY,
    title VARCHAR(50) NOT NULL CHECK (title <> '') UNIQUE,
    note  VARCHAR(250)
);

CREATE TABLE round
(
    id             UUID PRIMARY KEY,
    workout_schema JSONB       NOT NULL,
    title          VARCHAR(50) NOT NULL CHECK (title <> '') UNIQUE,
    note           VARCHAR(250)
);

CREATE TABLE workout
(
    id             UUID PRIMARY KEY,
    workout_schema JSONB       NOT NULL,
    date_time      TIMESTAMP   NOT NULL,
    title          VARCHAR(50) NOT NULL CHECK (title <> ''),
    note           VARCHAR(250)
);

