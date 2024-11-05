package com.example.m450.lb1.domain.airline;

import org.springframework.stereotype.Component;

@Component
public class AirlineMapper {

    public Airline toEntity(AirlineDTO.AirlineRequest dto) {
        return Airline.builder()
                .icao(dto.getIcao())
                .name(dto.getName())
                .country(dto.getCountry())
                .build();
    }

    public AirlineDTO.AirlineResponse toDto(Airline entity) {
        return AirlineDTO.AirlineResponse.builder()
                .id(entity.getId())
                .icao(entity.getIcao())
                .name(entity.getName())
                .country(entity.getCountry())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
