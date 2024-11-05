package com.example.m450.lb1.domain.seat;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.m450.lb1.exception.ResourceAlreadyExistsException;
import com.example.m450.lb1.exception.ResourceNotFoundException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seat")
public class SeatController {
    private final SeatService seatService;

    @GetMapping
    public ResponseEntity<List<SeatDTO.SeatResponse>> getAllSeats() {
        return ResponseEntity.ok(seatService.getAllSeats());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatDTO.SeatResponse> getSeatById(@PathVariable Long id)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(seatService.getSeatById(id));
    }

    @PostMapping
    public ResponseEntity<SeatDTO.SeatResponse> createSeat(
            @Valid @RequestBody SeatDTO.SeatRequest request) throws ResourceAlreadyExistsException {
        return ResponseEntity.ok(seatService.createSeat(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeatDTO.SeatResponse> updateSeat(
            @PathVariable Long id,
            @Valid @RequestBody SeatDTO.SeatRequest request)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(seatService.updateSeat(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeat(@PathVariable Long id)
            throws ResourceNotFoundException {
        seatService.deleteSeat(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/flightId/{flightId}")
    public ResponseEntity<List<SeatDTO.SeatResponse>> getSeatsByFlightId(@PathVariable Long flightId) {
        return ResponseEntity.ok(seatService.getSeatsByFlightId(flightId));
    }

    @GetMapping("/flightId/{flightId}/available")
    public ResponseEntity<List<SeatDTO.SeatResponse>> getAvailableSeatsByFlightId(@PathVariable Long flightId) {
        return ResponseEntity.ok(seatService.getAvailableSeatsByFlightId(flightId));
    }

    @GetMapping("/flightId/{flightId}/occupied")
    public ResponseEntity<List<SeatDTO.SeatResponse>> getOccupiedSeatsByFlightId(@PathVariable Long flightId) {
        return ResponseEntity.ok(seatService.getOccupiedSeatsByFlightId(flightId));
    }

    @GetMapping("/flightId/{flightId}/passengerId/{passengerId}")
    public ResponseEntity<SeatDTO.SeatResponse> getSeatByFlightIdAndPassengerId(
            @PathVariable Long flightId,
            @PathVariable Long passengerId) throws ResourceNotFoundException {
        return ResponseEntity.ok(seatService.getSeatsByFlightIdAndPassengerId(flightId, passengerId));
    }

    @PostMapping("/flightId/{flightId}/seatNumber/{seatNumber}/assign/{passengerId}")
    public ResponseEntity<SeatDTO.SeatResponse> assignSeatToPassenger(
            @PathVariable Long flightId,
            @PathVariable String seatNumber,
            @PathVariable Long passengerId) throws ResourceNotFoundException {
        seatService.assignSeatToPassenger(flightId, seatNumber, passengerId);
        return ResponseEntity.ok().build();
    }

}
