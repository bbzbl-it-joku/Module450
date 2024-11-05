package com.example.m450.lb1.domain.airline;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AirlineDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AirlineRequest {
        @NotBlank(message = "ICAO is required")
        private String icao;

        @NotBlank(message = "Airline name is required")
        private String name;

        @NotBlank(message = "Country is required")
        private String country;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AirlineResponse {
        private Long id;
        private String icao;
        private String name;
        private String country;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
