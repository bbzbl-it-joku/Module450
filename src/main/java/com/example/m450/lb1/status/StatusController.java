package com.example.m450.lb1.status;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/status")
public class StatusController {

    private final Clock clock;

    public StatusController(Clock clock) {
        this.clock = clock;
    }

    @GetMapping
    public ResponseEntity<LocalDateTime> getCurrentDateTime() {
        return ResponseEntity.ok(LocalDateTime.now(clock));
    }
}
