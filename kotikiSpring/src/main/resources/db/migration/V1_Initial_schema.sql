CREATE TABLE IF NOT EXISTS owners (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS pets (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    breed VARCHAR(255) NOT NULL,
    color VARCHAR(255) NOT NULL,
    owner_id BIGINT REFERENCES owners(id),
    tail_length DOUBLE PRECISION
    );

CREATE TABLE IF NOT EXISTS pet_friends (
    pet_id BIGINT NOT NULL REFERENCES pets(id),
    friend_id BIGINT NOT NULL REFERENCES pets(id),
    PRIMARY KEY (pet_id, friend_id)
    );