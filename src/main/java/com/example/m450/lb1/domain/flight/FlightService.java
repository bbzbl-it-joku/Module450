package com.example.m450.lb1.domain.flight;

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
public class FlightService {
    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    public List<FlightDTO.FlightResponse> getAllFlights() {
        return flightRepository.findAll().stream()
                .map(flightMapper::toDTO)
                .collect(Collectors.toList());
    }

    public FlightDTO.FlightResponse createFlight(FlightDTO.FlightRequest request)
            throws ResourceAlreadyExistsException {
        if (flightRepository.existsByFlightNumber(request.getFlightNumber())) {
            throw new ResourceAlreadyExistsException("Flight", "flightNumber", request.getFlightNumber());
        }

        Flight flight = flightMapper.toEntity(request);
        Flight savedFlight = flightRepository.save(flight);
        return flightMapper.toDTO(savedFlight);
    }

    public FlightDTO.FlightResponse updateFlight(Long id, FlightDTO.FlightRequest request)
            throws ResourceNotFoundException {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight", "id", id));
        flight.setFlightNumber(request.getFlightNumber());
        flight.setAircraftId(request.getAircraftId());
        flight.setDepartureTime(request.getDepartureTime());
        flight.setEstimatedArrivalTime(request.getEstimatedArrivalTime());
        flight.setDestination(request.getDestination());
        flight.setOrigin(request.getOrigin());
        Flight updatedFlight = flightRepository.save(flight);
        return flightMapper.toDTO(updatedFlight);
    }

    public void deleteFlight(Long id) throws ResourceNotFoundException {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight", "id", id));
        flightRepository.delete(flight);
    }

    public FlightDTO.FlightResponse getFlightById(Long id) throws ResourceNotFoundException {
        return flightRepository.findById(id)
                .map(flightMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Flight", "id", id));
    }

    public FlightDTO.FlightResponse getFlightByFlightNumber(String flightNumber) throws ResourceNotFoundException {
        return flightRepository.findByFlightNumber(flightNumber)
                .map(flightMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Flight", "flightNumber", flightNumber));
    }

    public List<FlightDTO.FlightResponse> getFlightsByOrigin(String origin) {
        List<Flight> flights = flightRepository.findByOrigin(origin);
        return flights.stream()
                .map(flightMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<FlightDTO.FlightResponse> getFlightsByDestination(String destination) {
        List<Flight> flights = flightRepository.findByDestination(destination);
        return flights.stream()
                .map(flightMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<FlightDTO.FlightResponse> getFlightsByOriginAndDestination(String origin, String destination) {
        List<Flight> flights = flightRepository.findByOriginAndDestination(origin, destination);
        return flights.stream()
                .map(flightMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<FlightDTO.FlightResponse> getFlightsByDepartureTimeBetweenAndOrigin(
            FlightDTO.TimeBetweenRequest timeBetweenRequest, String origin) {
        List<Flight> flights = flightRepository.findByDepartureTimeBetweenAndOrigin(timeBetweenRequest.getStart(),
                timeBetweenRequest.getEnd(), origin);
        return flights.stream()
                .map(flightMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<FlightDTO.FlightResponse> getFlightsByEstimatedArrivalTimeBetweenAndDestination(
            FlightDTO.TimeBetweenRequest timeBetweenRequest, String destination) {
        List<Flight> flights = flightRepository.findByEstimatedArrivalTimeBetweenAndDestination(
                timeBetweenRequest.getStart(), timeBetweenRequest.getEnd(), destination);
        return flights.stream()
                .map(flightMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<FlightDTO.FlightResponse> getFlightsByAircraftId(Long aircraftId) {
        List<Flight> flights = flightRepository.findByAircraftId(aircraftId);
        return flights.stream()
                .map(flightMapper::toDTO)
                .collect(Collectors.toList());
    }

    public boolean existsByOriginAndDestination(String origin, String destination) {
        return flightRepository.existsByOriginAndDestination(origin, destination);
    }

    public boolean existsByFlightNumber(String flightNumber) {
        return flightRepository.existsByFlightNumber(flightNumber);
    }
}
