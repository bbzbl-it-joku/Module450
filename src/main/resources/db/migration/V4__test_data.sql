BEGIN;

-- Airlines
INSERT INTO airline (icao, name, country) VALUES
    ('SWR', 'Swiss International Air Lines', 'Switzerland'),
    ('DLH', 'Lufthansa', 'Germany'),
    ('AUA', 'Austrian Airlines', 'Austria'),
    ('EZY', 'EasyJet', 'United Kingdom'),
    ('BAW', 'British Airways', 'United Kingdom');

-- Aircraft
INSERT INTO aircraft (registration_code, registration_prefix, airline_id, type, capacity)
SELECT 'HB-JCN', 'HB', id, 'Airbus A320neo', 180 FROM airline WHERE icao = 'SWR'
UNION ALL
SELECT 'HB-JCK', 'HB', id, 'Airbus A320neo', 180 FROM airline WHERE icao = 'SWR'
UNION ALL
SELECT 'D-AIEF', 'D', id, 'Airbus A320', 168 FROM airline WHERE icao = 'DLH'
UNION ALL
SELECT 'D-AIUL', 'D', id, 'Airbus A320neo', 180 FROM airline WHERE icao = 'DLH'
UNION ALL
SELECT 'OE-LBK', 'OE', id, 'Airbus A320', 174 FROM airline WHERE icao = 'AUA'
UNION ALL
SELECT 'G-UZHA', 'G', id, 'Airbus A320neo', 186 FROM airline WHERE icao = 'EZY'
UNION ALL
SELECT 'G-TTNA', 'G', id, 'Airbus A320neo', 180 FROM airline WHERE icao = 'BAW';

-- Passengers
INSERT INTO passenger (first_name, last_name, email, address) VALUES
    ('John', 'Smith', 'john.smith@email.com', '123 Main St, London, UK'),
    ('Marie', 'Dupont', 'marie.dupont@email.com', '45 Rue de Paris, Paris, France'),
    ('Hans', 'Mueller', 'hans.mueller@email.com', 'Hauptstrasse 1, Berlin, Germany'),
    ('Anna', 'Johnson', 'anna.johnson@email.com', '789 Park Avenue, New York, USA'),
    ('Luigi', 'Rossi', 'luigi.rossi@email.com', 'Via Roma 23, Rome, Italy'),
    ('Sophie', 'Martin', 'sophie.martin@email.com', '67 Rue du Commerce, Geneva, Switzerland'),
    ('James', 'Wilson', 'james.wilson@email.com', '456 High Street, Manchester, UK'),
    ('Maria', 'Garcia', 'maria.garcia@email.com', 'Calle Mayor 12, Madrid, Spain'),
    ('Thomas', 'Anderson', 'thomas.anderson@email.com', 'Bahnhofstrasse 15, Zurich, Switzerland'),
    ('Emma', 'Brown', 'emma.brown@email.com', '321 Queen Street, Brisbane, Australia');

-- Flights (with timestamps)
INSERT INTO flight (flight_number, aircraft_id, departure_time, estimated_arrival_time, origin, destination)
SELECT 
    'LX316',
    (SELECT id FROM aircraft WHERE registration_code = 'HB-JCN'),
    CAST('2024-06-01 07:00:00' AS TIMESTAMP),
    CAST('2024-06-01 08:15:00' AS TIMESTAMP),
    'ZRH',
    'LHR'
UNION ALL
SELECT 
    'LH1274',
    (SELECT id FROM aircraft WHERE registration_code = 'D-AIEF'),
    CAST('2024-06-01 08:30:00' AS TIMESTAMP),
    CAST('2024-06-01 09:45:00' AS TIMESTAMP),
    'FRA',
    'ZRH'
UNION ALL
SELECT 
    'OS561',
    (SELECT id FROM aircraft WHERE registration_code = 'OE-LBK'),
    CAST('2024-06-01 10:15:00' AS TIMESTAMP),
    CAST('2024-06-01 11:45:00' AS TIMESTAMP),
    'VIE',
    'CDG'
UNION ALL
SELECT 
    'U23087',
    (SELECT id FROM aircraft WHERE registration_code = 'G-UZHA'),
    CAST('2024-06-01 11:30:00' AS TIMESTAMP),
    CAST('2024-06-01 12:45:00' AS TIMESTAMP),
    'LGW',
    'BCN'
UNION ALL
SELECT 
    'BA733',
    (SELECT id FROM aircraft WHERE registration_code = 'G-TTNA'),
    CAST('2024-06-01 14:20:00' AS TIMESTAMP),
    CAST('2024-06-01 15:35:00' AS TIMESTAMP),
    'LHR',
    'ZRH';

-- Seats
INSERT INTO seat (seat_number, flight_id)
SELECT '1A', (SELECT id FROM flight WHERE flight_number = 'LX316')
UNION ALL
SELECT '1B', (SELECT id FROM flight WHERE flight_number = 'LX316')
UNION ALL
SELECT '2A', (SELECT id FROM flight WHERE flight_number = 'LX316')
UNION ALL
SELECT '2B', (SELECT id FROM flight WHERE flight_number = 'LX316')
UNION ALL
SELECT '3A', (SELECT id FROM flight WHERE flight_number = 'LX316')
UNION ALL
SELECT '3B', (SELECT id FROM flight WHERE flight_number = 'LX316')
UNION ALL
SELECT '4A', (SELECT id FROM flight WHERE flight_number = 'LX316')
UNION ALL
SELECT '4B', (SELECT id FROM flight WHERE flight_number = 'LX316')
UNION ALL
SELECT '1A', (SELECT id FROM flight WHERE flight_number = 'LH1274')
UNION ALL
SELECT '1B', (SELECT id FROM flight WHERE flight_number = 'LH1274')
UNION ALL
SELECT '2A', (SELECT id FROM flight WHERE flight_number = 'LH1274')
UNION ALL
SELECT '2B', (SELECT id FROM flight WHERE flight_number = 'LH1274')
UNION ALL
SELECT '3A', (SELECT id FROM flight WHERE flight_number = 'LH1274')
UNION ALL
SELECT '3B', (SELECT id FROM flight WHERE flight_number = 'LH1274')
UNION ALL
SELECT '4A', (SELECT id FROM flight WHERE flight_number = 'LH1274')
UNION ALL
SELECT '4B', (SELECT id FROM flight WHERE flight_number = 'LH1274')
UNION ALL
SELECT '1A', (SELECT id FROM flight WHERE flight_number = 'OS561')
UNION ALL
SELECT '1B', (SELECT id FROM flight WHERE flight_number = 'OS561')
UNION ALL
SELECT '2A', (SELECT id FROM flight WHERE flight_number = 'OS561')
UNION ALL
SELECT '2B', (SELECT id FROM flight WHERE flight_number = 'OS561')
UNION ALL
SELECT '3A', (SELECT id FROM flight WHERE flight_number = 'OS561')
UNION ALL
SELECT '3B', (SELECT id FROM flight WHERE flight_number = 'OS561')
UNION ALL
SELECT '4A', (SELECT id FROM flight WHERE flight_number = 'OS561')
UNION ALL
SELECT '4B', (SELECT id FROM flight WHERE flight_number = 'OS561')
UNION ALL
SELECT '1A', (SELECT id FROM flight WHERE flight_number = 'BA733')
UNION ALL
SELECT '1B', (SELECT id FROM flight WHERE flight_number = 'BA733')
UNION ALL
SELECT '2A', (SELECT id FROM flight WHERE flight_number = 'BA733')
UNION ALL
SELECT '2B', (SELECT id FROM flight WHERE flight_number = 'BA733')
UNION ALL
SELECT '3A', (SELECT id FROM flight WHERE flight_number = 'BA733')
UNION ALL
SELECT '3B', (SELECT id FROM flight WHERE flight_number = 'BA733')
UNION ALL
SELECT '4A', (SELECT id FROM flight WHERE flight_number = 'BA733')
UNION ALL
SELECT '4B', (SELECT id FROM flight WHERE flight_number = 'BA733');


-- Passengers seated
UPDATE seat SET passenger_id = (SELECT id FROM passenger WHERE first_name = 'John' AND last_name = 'Smith') WHERE seat_number = '1A' AND flight_id = (SELECT id FROM flight WHERE flight_number = 'LX316');
UPDATE seat SET passenger_id = (SELECT id FROM passenger WHERE first_name = 'Marie' AND last_name = 'Dupont') WHERE seat_number = '1B' AND flight_id = (SELECT id FROM flight WHERE flight_number = 'LX316');
UPDATE seat SET passenger_id = (SELECT id FROM passenger WHERE first_name = 'Hans' AND last_name = 'Mueller') WHERE seat_number = '2A' AND flight_id = (SELECT id FROM flight WHERE flight_number = 'LX316');
UPDATE seat SET passenger_id = (SELECT id FROM passenger WHERE first_name = 'Anna' AND last_name = 'Johnson') WHERE seat_number = '2B' AND flight_id = (SELECT id FROM flight WHERE flight_number = 'LX316');

UPDATE seat SET passenger_id = (SELECT id FROM passenger WHERE first_name = 'Luigi' AND last_name = 'Rossi') WHERE seat_number = '1A' AND flight_id = (SELECT id FROM flight WHERE flight_number = 'LH1274');
UPDATE seat SET passenger_id = (SELECT id FROM passenger WHERE first_name = 'Sophie' AND last_name = 'Martin') WHERE seat_number = '1B' AND flight_id = (SELECT id FROM flight WHERE flight_number = 'LH1274');
UPDATE seat SET passenger_id = (SELECT id FROM passenger WHERE first_name = 'James' AND last_name = 'Wilson') WHERE seat_number = '2A' AND flight_id = (SELECT id FROM flight WHERE flight_number = 'LH1274');
UPDATE seat SET passenger_id = (SELECT id FROM passenger WHERE first_name = 'Maria' AND last_name = 'Garcia') WHERE seat_number = '2B' AND flight_id = (SELECT id FROM flight WHERE flight_number = 'LH1274');

COMMIT;