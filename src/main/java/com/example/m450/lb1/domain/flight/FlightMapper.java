package com.example.m450.lb1.domain.flight;

import org.springframework.stereotype.Component;

@Component
public class FlightMapper {

    public Flight toEntity(FlightDTO.FlightRequest dto) {
        return Flight.builder()
                .flightNumber(dto.getFlightNumber())
                .aircraftId(dto.getAircraftId())
                .departureTime(dto.getDepartureTime())
                .estimatedArrivalTime(dto.getEstimatedArrivalTime())
                .destination(dto.getDestination())
                .origin(dto.getOrigin())
                .build();
    }

    public FlightDTO.FlightResponse toDTO(Flight entity) {
        return FlightDTO.FlightResponse.builder()
                .id(entity.getId())
                .flightNumber(entity.getFlightNumber())
                .aircraftId(entity.getAircraftId())
                .departureTime(entity.getDepartureTime())
                .estimatedArrivalTime(entity.getEstimatedArrivalTime())
                .destination(entity.getDestination())
                .origin(entity.getOrigin())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
