package com.example.m450.lb1.domain.airline;

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

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/airline")
public class AirlineController {
    private final AirlineService airlineService;

    @GetMapping
    public ResponseEntity<List<AirlineDTO.AirlineResponse>> getAllAirlines() {
        return ResponseEntity.ok(airlineService.getAllAirlines());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirlineDTO.AirlineResponse> getAirlineById(@PathVariable Long id)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(airlineService.getAirlineById(id));
    }

    @PostMapping
    public ResponseEntity<AirlineDTO.AirlineResponse> createAirline(
            @RequestBody AirlineDTO.AirlineRequest request) throws ValidationException, ResourceAlreadyExistsException {
        return ResponseEntity.ok(airlineService.createAirline(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AirlineDTO.AirlineResponse> updateAirline(
            @PathVariable Long id,
            @RequestBody AirlineDTO.AirlineRequest request)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(airlineService.updateAirline(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAirline(@PathVariable Long id)
            throws ResourceNotFoundException {
        airlineService.deleteAirline(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/icao/{icao}")
    public ResponseEntity<AirlineDTO.AirlineResponse> getAirlineByIcao(@PathVariable String icao)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(airlineService.getAirlineByIcao(icao));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<AirlineDTO.AirlineResponse> getAirlineByName(@PathVariable String name)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(airlineService.getAirlineByName(name));
    }

    @GetMapping("/country/{country}")
    public ResponseEntity<List<AirlineDTO.AirlineResponse>> getAirlinesByCountry(@PathVariable String country) {
        return ResponseEntity.ok(airlineService.getAirlinesByCountry(country));
    }

    @GetMapping("/exists/icao/{icao}")
    public ResponseEntity<Boolean> existsByIcao(@PathVariable String icao) {
        return ResponseEntity.ok(airlineService.existsAirlineByIcao(icao));
    }

    @GetMapping("/exists/name/{name}")
    public ResponseEntity<Boolean> existsByName(@PathVariable String name) {
        return ResponseEntity.ok(airlineService.existsAirlineByName(name));
    }
}
