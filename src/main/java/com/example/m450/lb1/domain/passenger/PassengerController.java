package com.example.m450.lb1.domain.passenger;

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
@RequestMapping("/api/passenger")
public class PassengerController {
    private final PassengerService passengerService;

    @GetMapping
    public ResponseEntity<List<PassengerDTO.PassengerResponse>> getAllPassengers() {
        return ResponseEntity.ok(passengerService.getAllPassengers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassengerDTO.PassengerResponse> getPassengerById(@PathVariable Long id)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(passengerService.getPassengerById(id));
    }

    @PostMapping
    public ResponseEntity<PassengerDTO.PassengerResponse> createPassenger(
            @Valid @RequestBody PassengerDTO.PassengerRequest request) throws ResourceAlreadyExistsException {
        return ResponseEntity.ok(passengerService.createPassenger(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PassengerDTO.PassengerResponse> updatePassenger(
            @PathVariable Long id,
            @Valid @RequestBody PassengerDTO.PassengerRequest request)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(passengerService.updatePassenger(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id)
            throws ResourceNotFoundException {
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/firstName/{firstName}")
    public ResponseEntity<List<PassengerDTO.PassengerResponse>> getPassengersByFirstName(
            @PathVariable String firstName) {
        return ResponseEntity.ok(passengerService.getPassengersByFirstName(firstName));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<PassengerDTO.PassengerResponse> getPassengerByEmail(@PathVariable String email)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(passengerService.getPassengerByEmail(email));
    }

    @GetMapping("/address/{address}")
    public ResponseEntity<List<PassengerDTO.PassengerResponse>> getPassengersByAddress(@PathVariable String address) {
        return ResponseEntity.ok(passengerService.getPassengersByAddress(address));
    }

    @GetMapping("/lastName/{lastName}")
    public ResponseEntity<List<PassengerDTO.PassengerResponse>> getPassengersByLastName(@PathVariable String lastName) {
        return ResponseEntity.ok(passengerService.getPassengersByLastName(lastName));
    }

    @GetMapping("/firstName/{firstName}/lastName/{lastName}")
    public ResponseEntity<List<PassengerDTO.PassengerResponse>> getPassengersByFirstNameAndLastName(
            @PathVariable String firstName, @PathVariable String lastName) {
        return ResponseEntity.ok(passengerService.getPassengersByFirstNameAndLastName(firstName, lastName));
    }
}
