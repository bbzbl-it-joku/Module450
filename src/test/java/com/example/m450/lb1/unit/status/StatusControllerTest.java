package com.example.m450.lb1.unit.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.reset;
import org.springframework.http.ResponseEntity;

import com.example.m450.lb1.status.StatusController;

class StatusControllerTest {

    private Clock clock;
    private StatusController statusController;
    private Instant fixedInstant;

    @BeforeEach
    void setUp() {
        // Freeze time at a fixed instant
        clock = Mockito.mock(Clock.class);
        fixedInstant = ZonedDateTime.of(2023, 11, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toInstant();
        when(clock.instant()).thenReturn(fixedInstant);
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());

        // Initialize the controller with the frozen clock
        statusController = new StatusController(clock);
    }

    @Test
    void testGetUptime() {
        // First call: uptime should be 00:00:00 since time is frozen
        ResponseEntity<String> initialResponse = statusController.getUptime();
        assertEquals("00:00:00", initialResponse.getBody());

        // "Advance" the time by changing what the mock returns for subsequent calls
        reset(clock);
        when(clock.instant()).thenReturn(fixedInstant.plus(Duration.ofHours(2).plusMinutes(30).plusSeconds(45)));
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());

        // Second call: uptime should be 02:30:45
        ResponseEntity<String> laterResponse = statusController.getUptime();
        assertEquals("02:30:45", laterResponse.getBody());
    }
}
