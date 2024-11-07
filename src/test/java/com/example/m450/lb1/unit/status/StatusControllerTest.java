package com.example.m450.lb1.unit.status;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.m450.lb1.status.StatusController;

class StatusControllerTest {

    private Clock clock;
    private StatusController statusController;
    private Instant fixedInstant;
    private final ZoneId TEST_ZONE = ZoneId.systemDefault();

    @BeforeEach
    void setUp() {
        clock = Mockito.mock(Clock.class);
        fixedInstant = ZonedDateTime.of(2023, 11, 1, 0, 0, 0, 0, TEST_ZONE).toInstant();
        when(clock.instant()).thenReturn(fixedInstant);
        when(clock.getZone()).thenReturn(TEST_ZONE);
        statusController = new StatusController(clock);
    }

    @Test
    @DisplayName("Test status endpoint returns OK")
    void testGetStatus() {
        ResponseEntity<String> response = statusController.getStatus();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("OK", response.getBody());
    }

    @Test
    @DisplayName("Test initial uptime is zero")
    void testInitialUptime() {
        ResponseEntity<String> response = statusController.getUptime();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("00:00:00", response.getBody());
    }

    @Test
    @DisplayName("Test uptime after 2 hours, 30 minutes, and 45 seconds")
    void testUptimeAfterTwoHoursThirtyMinutes() {
        // Advance time by 2:30:45
        reset(clock);
        when(clock.instant()).thenReturn(fixedInstant.plus(Duration.ofHours(2).plusMinutes(30).plusSeconds(45)));
        when(clock.getZone()).thenReturn(TEST_ZONE);

        ResponseEntity<String> response = statusController.getUptime();
        assertEquals("02:30:45", response.getBody());
    }

    @Test
    @DisplayName("Test uptime for exactly 24 hours")
    void testUptimeAfterOneDay() {
        reset(clock);
        when(clock.instant()).thenReturn(fixedInstant.plus(Duration.ofDays(1)));
        when(clock.getZone()).thenReturn(TEST_ZONE);

        ResponseEntity<String> response = statusController.getUptime();
        assertEquals("24:00:00", response.getBody());
    }

    @Test
    @DisplayName("Test uptime for multiple days")
    void testUptimeAfterMultipleDays() {
        reset(clock);
        // Set time to 48 hours, 30 minutes, and 15 seconds after start
        when(clock.instant()).thenReturn(fixedInstant.plus(
                Duration.ofDays(2)
                        .plusMinutes(30)
                        .plusSeconds(15)));
        when(clock.getZone()).thenReturn(TEST_ZONE);

        ResponseEntity<String> response = statusController.getUptime();
        assertEquals("48:30:15", response.getBody());
    }

    @Test
    @DisplayName("Test uptime for only seconds")
    void testUptimeSeconds() {
        reset(clock);
        when(clock.instant()).thenReturn(fixedInstant.plus(Duration.ofSeconds(45)));
        when(clock.getZone()).thenReturn(TEST_ZONE);

        ResponseEntity<String> response = statusController.getUptime();
        assertEquals("00:00:45", response.getBody());
    }

    @Test
    @DisplayName("Test uptime for only minutes")
    void testUptimeMinutes() {
        reset(clock);
        when(clock.instant()).thenReturn(fixedInstant.plus(Duration.ofMinutes(30)));
        when(clock.getZone()).thenReturn(TEST_ZONE);

        ResponseEntity<String> response = statusController.getUptime();
        assertEquals("00:30:00", response.getBody());
    }

    @Test
    @DisplayName("Test uptime formatting with single digits")
    void testUptimeFormattingSingleDigits() {
        reset(clock);
        when(clock.instant()).thenReturn(fixedInstant.plus(
                Duration.ofHours(5)
                        .plusMinutes(5)
                        .plusSeconds(5)));
        when(clock.getZone()).thenReturn(TEST_ZONE);

        ResponseEntity<String> response = statusController.getUptime();
        assertEquals("05:05:05", response.getBody());
    }
}