package com.example.m450.lb1.domain.flight;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends ListCrudRepository<Flight, Long> {
    // Built-in methods from ListCrudRepository:
    // List<Flight> findAll()
    // List<Flight> findAllById(Iterable<Long> ids)
    // <S extends Flight> List<S> saveAll(Iterable<S> entities)

    // Custom query methods
    Optional<Flight> findByFlightNumber(String flightNumber);

    List<Flight> findByAircraftId(Long aircraftId);

    List<Flight> findByDestination(String destination);

    List<Flight> findByOrigin(String origin);

    List<Flight> findByOriginAndDestination(String origin, String destination);

    List<Flight> findByDepartureTimeBetweenAndOrigin(LocalDateTime start, LocalDateTime end, String origin);

    List<Flight> findByEstimatedArrivalTimeBetweenAndDestination(LocalDateTime start, LocalDateTime end,
            String destination);

    boolean existsByFlightNumber(String flightNumber);

    boolean existsByOriginAndDestination(String origin, String destination);
}