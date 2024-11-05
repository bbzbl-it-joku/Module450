package com.example.m450.lb1.domain.flight;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class FlightDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FlightRequest {
        @NotBlank(message = "Flight number is required")
        private String flightNumber;

        @NotNull(message = "Aircraft ID is required")
        private Long aircraftId;

        @NotNull(message = "Departure time is required")
        private LocalDateTime departureTime;

        @NotNull(message = "Estimated arrival time is required")
        private LocalDateTime estimatedArrivalTime;

        @NotBlank(message = "Destination is required")
        private String destination;

        @NotBlank(message = "Origin is required")
        private String origin;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TimeBetweenRequest {
        @NotNull(message = "Start time is required")
        private LocalDateTime start;

        @NotNull(message = "End time is required")
        private LocalDateTime end;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FlightResponse {
        private Long id;
        private String flightNumber;
        private Long aircraftId;
        private LocalDateTime departureTime;
        private LocalDateTime estimatedArrivalTime;
        private String destination;
        private String origin;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
