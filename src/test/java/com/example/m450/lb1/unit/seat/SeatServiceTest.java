package com.example.m450.lb1.unit.seat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.m450.lb1.domain.seat.Seat;
import com.example.m450.lb1.domain.seat.SeatDTO;
import com.example.m450.lb1.domain.seat.SeatMapper;
import com.example.m450.lb1.domain.seat.SeatRepository;
import com.example.m450.lb1.domain.seat.SeatService;

/**
 * LB1: 3a TDD
 */
@ExtendWith(MockitoExtension.class)
class SeatServiceTest {

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private SeatMapper seatMapper;

    @InjectMocks
    private SeatService seatService;

    private Seat availableSeat1;
    private Seat availableSeat2;
    private Seat occupiedSeat1;
    private Seat occupiedSeat2;
    private SeatDTO.SeatResponse availableSeatResponse1;
    private SeatDTO.SeatResponse availableSeatResponse2;
    private SeatDTO.SeatResponse occupiedSeatResponse1;
    private SeatDTO.SeatResponse occupiedSeatResponse2;

    @BeforeEach
    void setUp() {
        // Create test data for available seats
        availableSeat1 = new Seat();
        availableSeat1.setId(1L);
        availableSeat1.setSeatNumber("1A");
        availableSeat1.setFlightId(1L);
        availableSeat1.setPassengerId(null);

        availableSeat2 = new Seat();
        availableSeat2.setId(2L);
        availableSeat2.setSeatNumber("1B");
        availableSeat2.setFlightId(1L);
        availableSeat2.setPassengerId(null);

        // Create test data for occupied seats
        occupiedSeat1 = new Seat();
        occupiedSeat1.setId(3L);
        occupiedSeat1.setSeatNumber("2A");
        occupiedSeat1.setFlightId(1L);
        occupiedSeat1.setPassengerId(1L);

        occupiedSeat2 = new Seat();
        occupiedSeat2.setId(4L);
        occupiedSeat2.setSeatNumber("2B");
        occupiedSeat2.setFlightId(1L);
        occupiedSeat2.setPassengerId(2L);

        // Create corresponding DTO responses
        availableSeatResponse1 = new SeatDTO.SeatResponse(1L, "1A", 1L, null, LocalDateTime.now(), LocalDateTime.now());
        availableSeatResponse2 = new SeatDTO.SeatResponse(2L, "1B", 1L, null, LocalDateTime.now(), LocalDateTime.now());
        occupiedSeatResponse1 = new SeatDTO.SeatResponse(3L, "2A", 1L, 1L, LocalDateTime.now(), LocalDateTime.now());
        occupiedSeatResponse2 = new SeatDTO.SeatResponse(4L, "2B", 1L, 2L, LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void getAvailableSeatsByFlightId_ShouldReturnOnlyAvailableSeats() {
        // Arrange
        Long flightId = 1L;
        List<Seat> allSeats = Arrays.asList(availableSeat1, availableSeat2, occupiedSeat1, occupiedSeat2);
        
        when(seatRepository.findByFlightId(flightId)).thenReturn(allSeats);
        when(seatMapper.toDto(availableSeat1)).thenReturn(availableSeatResponse1);
        when(seatMapper.toDto(availableSeat2)).thenReturn(availableSeatResponse2);

        // Act
        List<SeatDTO.SeatResponse> result = seatService.getAvailableSeatsByFlightId(flightId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(availableSeatResponse1));
        assertTrue(result.contains(availableSeatResponse2));
        verify(seatRepository).findByFlightId(flightId);
        verify(seatMapper, times(2)).toDto(any(Seat.class));
    }

    @Test
    void getOccupiedSeatsByFlightId_ShouldReturnOnlyOccupiedSeats() {
        // Arrange
        Long flightId = 1L;
        List<Seat> allSeats = Arrays.asList(availableSeat1, availableSeat2, occupiedSeat1, occupiedSeat2);
        
        when(seatRepository.findByFlightId(flightId)).thenReturn(allSeats);
        when(seatMapper.toDto(occupiedSeat1)).thenReturn(occupiedSeatResponse1);
        when(seatMapper.toDto(occupiedSeat2)).thenReturn(occupiedSeatResponse2);

        // Act
        List<SeatDTO.SeatResponse> result = seatService.getOccupiedSeatsByFlightId(flightId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(occupiedSeatResponse1));
        assertTrue(result.contains(occupiedSeatResponse2));
        verify(seatRepository).findByFlightId(flightId);
        verify(seatMapper, times(2)).toDto(any(Seat.class));
    }

    @Test
    void getAvailableSeatsByFlightId_WhenNoAvailableSeats_ShouldReturnEmptyList() {
        // Arrange
        Long flightId = 1L;
        List<Seat> allSeats = Arrays.asList(occupiedSeat1, occupiedSeat2);
        
        when(seatRepository.findByFlightId(flightId)).thenReturn(allSeats);

        // Act
        List<SeatDTO.SeatResponse> result = seatService.getAvailableSeatsByFlightId(flightId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(seatRepository).findByFlightId(flightId);
        verify(seatMapper, never()).toDto(any(Seat.class));
    }

    @Test
    void getOccupiedSeatsByFlightId_WhenNoOccupiedSeats_ShouldReturnEmptyList() {
        // Arrange
        Long flightId = 1L;
        List<Seat> allSeats = Arrays.asList(availableSeat1, availableSeat2);
        
        when(seatRepository.findByFlightId(flightId)).thenReturn(allSeats);

        // Act
        List<SeatDTO.SeatResponse> result = seatService.getOccupiedSeatsByFlightId(flightId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(seatRepository).findByFlightId(flightId);
        verify(seatMapper, never()).toDto(any(Seat.class));
    }
}