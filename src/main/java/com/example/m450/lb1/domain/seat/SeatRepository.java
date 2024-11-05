package com.example.m450.lb1.domain.seat;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends ListCrudRepository<Seat, Long> {
    // Built-in methods from ListCrudRepository:
    // List<Seat> findAll()
    // List<Seat> findAllById(Iterable<Long> ids)
    // <S extends Seat> List<S> saveAll(Iterable<S> entities)

    // Custom query methods
    List<Seat> findByFlightId(Long flightId);

    List<Seat> findByPassengerId(Long passengerId);

    Optional<Seat> findBySeatNumberAndFlightId(String seatNumber, Long flightId);

    Optional<Seat> findByFlightIdAndPassengerId(Long flightId, Long passengerId);

    boolean existsBySeatNumberAndFlightId(String seatNumber, Long flightId);

}
