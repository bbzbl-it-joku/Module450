CREATE OR REPLACE FUNCTION update_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER airline_updated_at
    BEFORE UPDATE ON airline
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at();

CREATE TRIGGER aircraft_updated_at
    BEFORE UPDATE ON aircraft
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at();

CREATE TRIGGER passenger_updated_at
    BEFORE UPDATE ON passenger
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at();

CREATE TRIGGER flight_updated_at
    BEFORE UPDATE ON flight
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at();

CREATE TRIGGER seat_updated_at
    BEFORE UPDATE ON seat
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at();