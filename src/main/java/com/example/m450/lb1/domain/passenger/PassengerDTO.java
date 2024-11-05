package com.example.m450.lb1.domain.passenger;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class PassengerDTO {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PassengerRequest {
        @NotBlank(message = "First name is required")
        private String firstName;

        @NotBlank(message = "Last name is required")
        private String lastName;

        @NotBlank(message = "Email is required")
        private String email;

        @NotBlank(message = "Address is required")
        private String address;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PassengerResponse {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String address;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
