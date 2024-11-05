package com.example.m450.lb1.domain.airline;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.m450.lb1.exception.ResourceAlreadyExistsException;
import com.example.m450.lb1.exception.ResourceNotFoundException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AirlineService {
    private final AirlineRepository airlineRepository;
    private final AirlineMapper airlineMapper;

    public List<AirlineDTO.AirlineResponse> getAllAirlines() {
        return airlineRepository.findAll().stream()
                .map(airlineMapper::toDto)
                .collect(Collectors.toList());
    }

    public AirlineDTO.AirlineResponse createAirline(@Valid AirlineDTO.AirlineRequest request)
            throws ResourceAlreadyExistsException {
        if (airlineRepository.existsByIcao(request.getIcao())) {
            throw new ResourceAlreadyExistsException("Airline", "icao", request.getIcao());
        }
        if (airlineRepository.existsByName(request.getName())) {
            throw new ResourceAlreadyExistsException("Airline", "name", request.getName());
        }
        Airline airline = airlineMapper.toEntity(request);
        Airline savedAirline = airlineRepository.save(airline);
        return airlineMapper.toDto(savedAirline);
    }

    public AirlineDTO.AirlineResponse updateAirline(Long id, @Valid AirlineDTO.AirlineRequest request)
            throws ResourceNotFoundException {
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airline", "id", id));
        airline.setName(request.getName());
        airline.setCountry(request.getCountry());
        airline.setIcao(request.getIcao());
        Airline updatedAirline = airlineRepository.save(airline);
        return airlineMapper.toDto(updatedAirline);
    }

    public void deleteAirline(Long id) throws ResourceNotFoundException {
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airline", "id", id));
        airlineRepository.delete(airline);
    }

    public AirlineDTO.AirlineResponse getAirlineById(Long id) throws ResourceNotFoundException {
        return airlineRepository.findById(id)
                .map(airlineMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Airline", "id", id));
    }

    public AirlineDTO.AirlineResponse getAirlineByIcao(String icao) throws ResourceNotFoundException {
        return airlineRepository.findByIcao(icao)
                .map(airlineMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Airline", "icao", icao));
    }

    public AirlineDTO.AirlineResponse getAirlineByName(String name) throws ResourceNotFoundException {
        return airlineRepository.findByName(name)
                .map(airlineMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Airline", "name", name));
    }

    public List<AirlineDTO.AirlineResponse> getAirlinesByCountry(String country) {
        List<Airline> airlines = airlineRepository.findByCountry(country);
        return airlines.stream()
                .map(airlineMapper::toDto)
                .collect(Collectors.toList());
    }

    public boolean existsAirlineByIcao(String icao) {
        return airlineRepository.existsByIcao(icao);
    }

    public boolean existsAirlineByName(String name) {
        return airlineRepository.existsByName(name);
    }
}
