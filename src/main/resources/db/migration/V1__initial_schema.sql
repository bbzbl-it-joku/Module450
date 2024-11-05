CREATE TABLE airline (
    id BIGSERIAL PRIMARY KEY,
    icao VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL UNIQUE,
    country VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE aircraft (
    id BIGSERIAL PRIMARY KEY,
    registration_code VARCHAR(255) NOT NULL UNIQUE,
    registration_prefix VARCHAR(255) NOT NULL,
    airline_id BIGINT NOT NULL,
    type VARCHAR(255) NOT NULL,
    capacity INTEGER NOT NULL CHECK (capacity > 0),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (airline_id) REFERENCES airline(id)
);

CREATE TABLE passenger (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    address VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE flight (
    id BIGSERIAL PRIMARY KEY,
    flight_number VARCHAR(255) NOT NULL UNIQUE,
    aircraft_id BIGINT NOT NULL,
    departure_time TIMESTAMP NOT NULL,
    estimated_arrival_time TIMESTAMP NOT NULL,
    destination VARCHAR(255) NOT NULL,
    origin VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (aircraft_id) REFERENCES aircraft(id),
    CHECK (estimated_arrival_time > departure_time)
);

CREATE TABLE seat (
    id BIGSERIAL PRIMARY KEY,
    seat_number VARCHAR(255) NOT NULL,
    flight_id BIGINT NOT NULL,
    passenger_id BIGINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (flight_id) REFERENCES flight(id),
    FOREIGN KEY (passenger_id) REFERENCES passenger(id),
    UNIQUE (seat_number, flight_id)
);