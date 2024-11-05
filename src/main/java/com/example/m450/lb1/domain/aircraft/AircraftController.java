package com.example.m450.lb1.domain.aircraft;

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
@RequestMapping("/api/aircraft")
public class AircraftController {
    private final AircraftService aircraftService;

    @GetMapping
    public ResponseEntity<List<AircraftDTO.AircraftResponse>> getAllAircraft() {
        return ResponseEntity.ok(aircraftService.getAllAircraft());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AircraftDTO.AircraftResponse> getAircraftById(@PathVariable Long id)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(aircraftService.getAircraftById(id));
    }

    @PostMapping
    public ResponseEntity<AircraftDTO.AircraftResponse> createAircraft(
            @Valid @RequestBody AircraftDTO.AircraftRequest request)
            throws ValidationException, ResourceAlreadyExistsException {
        return ResponseEntity.ok(aircraftService.createAircraft(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AircraftDTO.AircraftResponse> updateAircraft(
            @PathVariable Long id,
            @Valid @RequestBody AircraftDTO.AircraftRequest request)
            throws ResourceNotFoundException, ValidationException {
        return ResponseEntity.ok(aircraftService.updateAircraft(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAircraft(@PathVariable Long id)
            throws ResourceNotFoundException {
        aircraftService.deleteAircraft(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/registrationCode/{registrationCode}")
    public ResponseEntity<AircraftDTO.AircraftResponse> getAircraftByRegistrationCode(
            @PathVariable String registrationCode) throws ResourceNotFoundException {
        return ResponseEntity.ok(aircraftService.getAircraftByRegistrationCode(registrationCode));
    }

    @GetMapping("/registrationPrefix/{registrationPrefix}")
    public ResponseEntity<List<AircraftDTO.AircraftResponse>> getAircraftByRegistrationPrefix(
            @PathVariable String registrationPrefix) {
        return ResponseEntity.ok(aircraftService.getAircraftByRegistrationPrefix(registrationPrefix));
    }

    @GetMapping("/airline/{airlineId}")
    public ResponseEntity<List<AircraftDTO.AircraftResponse>> getAircraftByAirline(
            @PathVariable Long airlineId) throws ResourceNotFoundException {
        return ResponseEntity.ok(aircraftService.getAircraftByAirline(airlineId));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<AircraftDTO.AircraftResponse>> getAircraftByType(
            @PathVariable String type) throws ResourceNotFoundException {
        return ResponseEntity.ok(aircraftService.getAircraftByType(type));
    }

    @GetMapping("/capacity/{capacity}")
    public ResponseEntity<List<AircraftDTO.AircraftResponse>> getAircraftByCapacity(
            @PathVariable int capacity) throws ResourceNotFoundException {
        return ResponseEntity.ok(aircraftService.getAircraftByCapacity(capacity));
    }

    @GetMapping("/capacity/{minCapacity}/{maxCapacity}")
    public ResponseEntity<List<AircraftDTO.AircraftResponse>> getAircraftByCapacityRange(
            @PathVariable int minCapacity, @PathVariable int maxCapacity)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(aircraftService.getAircraftByCapacityRange(minCapacity, maxCapacity));
    }

    @GetMapping("/airline/{airlineId}/type/{type}")
    public ResponseEntity<List<AircraftDTO.AircraftResponse>> getAircraftByAirlineAndType(
            @PathVariable Long airlineId, @PathVariable String type)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(aircraftService.getAircraftByAirlineAndType(airlineId, type));
    }
}