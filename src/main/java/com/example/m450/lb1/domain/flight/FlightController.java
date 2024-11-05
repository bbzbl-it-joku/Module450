package com.example.m450.lb1.domain.flight;

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
import com.example.m450.lb1.exception.ValidationException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flight")
public class FlightController {
    private final FlightService flightService;

    @GetMapping
    public ResponseEntity<List<FlightDTO.FlightResponse>> getAllFlights() {
        return ResponseEntity.ok(flightService.getAllFlights());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightDTO.FlightResponse> getFlightById(@PathVariable Long id)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @PostMapping
    public ResponseEntity<FlightDTO.FlightResponse> createFlight(
            @Valid @RequestBody FlightDTO.FlightRequest request)
            throws ValidationException, ResourceAlreadyExistsException {
        return ResponseEntity.ok(flightService.createFlight(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightDTO.FlightResponse> updateFlight(
            @PathVariable Long id,
            @Valid @RequestBody FlightDTO.FlightRequest request)
            throws ResourceNotFoundException, ValidationException {
        return ResponseEntity.ok(flightService.updateFlight(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id)
            throws ResourceNotFoundException {
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/flightNumber/{flightNumber}")
    public ResponseEntity<FlightDTO.FlightResponse> getFlightByFlightNumber(@PathVariable String flightNumber)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(flightService.getFlightByFlightNumber(flightNumber));
    }

    @GetMapping("/aircraft/{aircraftId}")
    public ResponseEntity<List<FlightDTO.FlightResponse>> getFlightsByAircraftId(@PathVariable Long aircraftId)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(flightService.getFlightsByAircraftId(aircraftId));
    }

    @GetMapping("/origin/{origin}")
    public ResponseEntity<List<FlightDTO.FlightResponse>> getFlightsByOrigin(@PathVariable String origin) {
        return ResponseEntity.ok(flightService.getFlightsByOrigin(origin));
    }

    @GetMapping("/destination/{destination}")
    public ResponseEntity<List<FlightDTO.FlightResponse>> getFlightsByDestination(@PathVariable String destination) {
        return ResponseEntity.ok(flightService.getFlightsByDestination(destination));
    }

    @GetMapping("/origin/{origin}/destination/{destination}")
    public ResponseEntity<List<FlightDTO.FlightResponse>> getFlightsByOriginAndDestination(
            @PathVariable String origin,
            @PathVariable String destination) {
        return ResponseEntity.ok(flightService.getFlightsByOriginAndDestination(origin, destination));
    }

    @GetMapping("/origin/{origin}/between")
    public ResponseEntity<List<FlightDTO.FlightResponse>> getFlightsByOriginAndDestinationBetween(
            @PathVariable String origin,
            @RequestBody @Valid FlightDTO.TimeBetweenRequest timeBetweenRequest) throws ValidationException {
        return ResponseEntity.ok(flightService.getFlightsByDepartureTimeBetweenAndOrigin(timeBetweenRequest, origin));
    }

    @GetMapping("/destination/{destination}/between")
    public ResponseEntity<List<FlightDTO.FlightResponse>> getFlightsByDestinationAndDestinationBetween(
            @PathVariable String destination,
            @RequestBody @Valid FlightDTO.TimeBetweenRequest timeBetweenRequest) throws ValidationException {
        return ResponseEntity.ok(
                flightService.getFlightsByEstimatedArrivalTimeBetweenAndDestination(timeBetweenRequest, destination));
    }

    @GetMapping("/exists/flightNumber/{flightNumber}")
    public ResponseEntity<Boolean> existsByFlightNumber(@PathVariable String flightNumber) {
        return ResponseEntity.ok(flightService.existsByFlightNumber(flightNumber));
    }

    @GetMapping("/exists/origin/{origin}/destination/{destination}")
    public ResponseEntity<Boolean> existsByOriginAndDestination(
            @PathVariable String origin,
            @PathVariable String destination) {
        return ResponseEntity.ok(flightService.existsByOriginAndDestination(origin, destination));
    }
}
