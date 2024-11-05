package com.example.m450.lb1.domain.seat;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.m450.lb1.exception.ResourceAlreadyExistsException;
import com.example.m450.lb1.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;

    public List<SeatDTO.SeatResponse> getAllSeats() {
        return seatRepository.findAll().stream()
                .map(seatMapper::toDto)
                .collect(Collectors.toList());
    }

    public SeatDTO.SeatResponse createSeat(SeatDTO.SeatRequest request) throws ResourceAlreadyExistsException {
        if (seatRepository.existsBySeatNumberAndFlightId(request.getSeatNumber(), request.getFlightId())) {
            throw new ResourceAlreadyExistsException("Seat", "seatNumber", request.getSeatNumber());
        }

        Seat seat = seatMapper.toEntity(request);
        Seat savedSeat = seatRepository.save(seat);
        return seatMapper.toDto(savedSeat);
    }

    public SeatDTO.SeatResponse updateSeat(Long id, SeatDTO.SeatRequest request) throws ResourceNotFoundException {
        Seat seat = seatRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Seat", "id", id));
        seat.setSeatNumber(request.getSeatNumber());
        seat.setFlightId(request.getFlightId());
        seat.setPassengerId(request.getPassengerId());
        Seat updatedSeat = seatRepository.save(seat);
        return seatMapper.toDto(updatedSeat);
    }

    public void deleteSeat(Long id) throws ResourceNotFoundException {
        Seat seat = seatRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Seat", "id", id));
        seatRepository.delete(seat);
    }

    public SeatDTO.SeatResponse getSeatById(Long id) throws ResourceNotFoundException {
        return seatRepository.findById(id).map(seatMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Seat", "id", id));
    }

    public List<SeatDTO.SeatResponse> getSeatsByFlightId(Long flightId) {
        return seatRepository.findByFlightId(flightId).stream()
                .map(seatMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<SeatDTO.SeatResponse> getSeatsByPassengerId(Long passengerId) {
        return seatRepository.findByPassengerId(passengerId).stream()
                .map(seatMapper::toDto)
                .collect(Collectors.toList());
    }

    public SeatDTO.SeatResponse getSeatBySeatNumberAndFlightId(String seatNumber, Long flightId)
            throws ResourceNotFoundException {
        return seatRepository.findBySeatNumberAndFlightId(seatNumber, flightId)
                .map(seatMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Seat", "seatNumber", seatNumber));
    }

    public SeatDTO.SeatResponse getSeatsByFlightIdAndPassengerId(Long flightId, Long passengerId) {
        return seatRepository.findByFlightIdAndPassengerId(flightId, passengerId)
                .map(seatMapper::toDto)
                .orElse(null);
    }

    public boolean isSeatAvailable(String seatNumber, Long flightId) {
        return !seatRepository.existsBySeatNumberAndFlightId(seatNumber, flightId);
    }

    public void assignSeatToPassenger(Long flightId, String seatNumber, Long passengerId)
            throws ResourceNotFoundException {
        Seat seat = seatRepository.findBySeatNumberAndFlightId(seatNumber, flightId)
                .orElseThrow(() -> new ResourceNotFoundException("Seat", "seatNumber", seatNumber));
        seat.setPassengerId(passengerId);
        seatRepository.save(seat);
    }

    public void unassignSeatFromPassenger(String seatNumber, Long flightId) throws ResourceNotFoundException {
        Seat seat = seatRepository.findBySeatNumberAndFlightId(seatNumber, flightId)
                .orElseThrow(() -> new ResourceNotFoundException("Seat", "seatNumber", seatNumber));
        seat.setPassengerId(null);
        seatRepository.save(seat);
    }

    /**
     * LB1: 3b TDD Impl
     */
    public List<SeatDTO.SeatResponse> getAvailableSeatsByFlightId(Long flightId) {
        return seatRepository.findByFlightId(flightId).stream()
                .filter(seat -> seat.getPassengerId() == null)
                .map(seatMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * LB1: 3b TDD Impl
     */
    public List<SeatDTO.SeatResponse> getOccupiedSeatsByFlightId(Long flightId) {
        return seatRepository.findByFlightId(flightId).stream()
                .filter(seat -> seat.getPassengerId() != null)
                .map(seatMapper::toDto)
                .collect(Collectors.toList());
    }
}
