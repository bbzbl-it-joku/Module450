package com.example.m450.lb1.domain.aircraft;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AircraftDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AircraftRequest {
        @NotBlank(message = "Registration code is required")
        private String registrationCode;

        @NotBlank(message = "Registration prefix is required")
        private String registrationPrefix;

        @NotNull(message = "Airline ID is required")
        private Long airlineId;

        @NotBlank(message = "Aircraft type is required")
        private String type;

        @NotNull(message = "Capacity is required")
        @Min(value = 1, message = "Capacity must be greater than 0")
        private Integer capacity;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AircraftResponse {
        private Long id;
        private String registrationCode;
        private String registrationPrefix;
        private Long airlineId;
        private String type;
        private int capacity;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}