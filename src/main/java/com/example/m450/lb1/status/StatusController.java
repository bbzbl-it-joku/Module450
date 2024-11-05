package com.example.m450.lb1.status;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.Duration;
import java.time.ZonedDateTime;

@RestController
@RequestMapping("/status")
public class StatusController {

    private final Clock clock;
    private final ZonedDateTime startTime;

    public StatusController(Clock clock) {
        this.clock = clock;
        this.startTime = ZonedDateTime.now(clock); // Initialized once at the controller creation
    }

    @GetMapping
    public ResponseEntity<String> getUptime() {
        // Calculate uptime
        Duration uptime = Duration.between(startTime, ZonedDateTime.now(clock));
        return ResponseEntity.ok(formatDuration(uptime));
    }

    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
