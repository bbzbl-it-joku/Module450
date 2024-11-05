package com.example.m450.lb1.domain.seat;

import org.springframework.stereotype.Component;

@Component
public class SeatMapper {

    public Seat toEntity(SeatDTO.SeatRequest dto) {
        return Seat.builder()
                .seatNumber(dto.getSeatNumber())
                .flightId(dto.getFlightId())
                .passengerId(dto.getPassengerId())
                .build();
    }

    public SeatDTO.SeatResponse toDto(Seat entity) {
        return SeatDTO.SeatResponse.builder()
                .id(entity.getId())
                .seatNumber(entity.getSeatNumber())
                .flightId(entity.getFlightId())
                .passengerId(entity.getPassengerId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
