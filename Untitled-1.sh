#!/usr/bin/env zsh

setopt ERR_EXIT
setopt PIPE_FAIL
setopt EXTENDED_GLOB
setopt WARN_CREATE_GLOBAL

# Configure flight numbers and their corresponding aircraft registrations
typeset -A FLIGHTS
FLIGHTS=(
    LX316 HB-JCN
    LH1274 D-AIEF
    OS561 OE-LBK
    U23087 G-UZHA
    BA733 G-TTNA
)

# Define seat rows and positions using zsh ranges
typeset -a ROWS POSITIONS
ROWS=({1..30})
POSITIONS=(A B C D E F)

# Output file
OUTPUT_FILE="V4__generated_seats.sql"

# Function to print timestamp in log messages
print_timestamp() {
    print -n "[$(date '+%Y-%m-%d %H:%M:%S')] "
}

# Function to log messages
log_message() {
    print_timestamp
    print "$1"
}

# Start writing to file
cat > $OUTPUT_FILE << 'EOH'
-- Generated Seat Insertions
-- This script was automatically generated
-- Generation Date: $(date '+%Y-%m-%d %H:%M:%S')

BEGIN;

-- Remove any existing seat data
DELETE FROM seat;

EOH

# Function to generate insert statement for a seat
generate_seat_insert() {
    local flight_number=$1
    local row=$2
    local position=$3
    
    print "INSERT INTO seat (seat_number, flight_id, passenger_id)"
    print "SELECT '${row}${position}',"
    print "       (SELECT id FROM flight WHERE flight_number = '${flight_number}'),"
    print "       NULL;"
}

# Progress counter
typeset -i total_seats=0
typeset -i current_seat=0
typeset -F 2 progress

# Calculate total number of seats
(( total_seats = $#FLIGHTS * $#ROWS * $#POSITIONS ))

# Generate insert statements for each flight and seat
for flight_number in ${(k)FLIGHTS}; do
    log_message "Generating seats for flight ${flight_number}"
    print "\n-- Generating seats for flight ${flight_number}" >> $OUTPUT_FILE
    
    for row in $ROWS; do
        for position in $POSITIONS; do
            generate_seat_insert $flight_number $row $position >> $OUTPUT_FILE
            print >> $OUTPUT_FILE
            
            # Update and display progress
            (( current_seat++ ))
            (( progress = (current_seat.0 / total_seats.0) * 100 ))
            printf "\rProgress: %5.1f%%" $progress
        done
    done
    
    print "\n-- End of seats for flight ${flight_number}" >> $OUTPUT_FILE
    print >> $OUTPUT_FILE
done

# Add transaction completion and specific assignments
cat >> $OUTPUT_FILE << 'EOH'

-- Add specific passenger assignments
UPDATE seat 
SET passenger_id = (SELECT id FROM passenger WHERE email = 'john.smith@email.com')
WHERE seat_number = '1A' 
AND flight_id = (SELECT id FROM flight WHERE flight_number = 'LX316');

UPDATE seat 
SET passenger_id = (SELECT id FROM passenger WHERE email = 'marie.dupont@email.com')
WHERE seat_number = '1B'
AND flight_id = (SELECT id FROM flight WHERE flight_number = 'LX316');

UPDATE seat 
SET passenger_id = (SELECT id FROM passenger WHERE email = 'hans.mueller@email.com')
WHERE seat_number = '1A'
AND flight_id = (SELECT id FROM flight WHERE flight_number = 'LH1274');

-- Randomly assign some passengers to remaining seats
UPDATE seat
SET passenger_id = (
    SELECT id 
    FROM passenger 
    ORDER BY random() 
    LIMIT 1
)
WHERE random() < 0.3 
AND passenger_id IS NULL;

COMMIT;
EOH

log_message "Generated SQL script saved to $OUTPUT_FILE"
print "\nTotal seats generated: $current_seat"

# Make the output file readable but not executable (since it's SQL)
chmod 644 $OUTPUT_FILE