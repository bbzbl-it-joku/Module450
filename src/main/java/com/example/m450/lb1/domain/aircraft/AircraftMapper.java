package com.example.m450.lb1.domain.aircraft;

import org.springframework.stereotype.Component;

import jakarta.validation.Valid;

@Component
public class AircraftMapper {

    public Aircraft toEntity(@Valid AircraftDTO.AircraftRequest dto) {
        return Aircraft.builder()
                .registrationCode(dto.getRegistrationCode())
                .registrationPrefix(dto.getRegistrationPrefix())
                .airlineId(dto.getAirlineId())
                .type(dto.getType())
                .capacity(dto.getCapacity())
                .build();
    }

    public AircraftDTO.AircraftResponse toDto(@Valid Aircraft entity) {
        return AircraftDTO.AircraftResponse.builder()
                .id(entity.getId())
                .registrationCode(entity.getRegistrationCode())
                .registrationPrefix(entity.getRegistrationPrefix())
                .airlineId(entity.getAirlineId())
                .type(entity.getType())
                .capacity(entity.getCapacity())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}