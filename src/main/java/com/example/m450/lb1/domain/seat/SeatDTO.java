package com.example.m450.lb1.domain.seat;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class SeatDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SeatRequest {
        @NotBlank(message = "Seat number is required")
        private String seatNumber;

        @NotNull(message = "Flight ID is required")
        private Long flightId;

        private Long passengerId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SeatResponse {
        private Long id;
        private String seatNumber;
        private Long flightId;
        private Long passengerId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
