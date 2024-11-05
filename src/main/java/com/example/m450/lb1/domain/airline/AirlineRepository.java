package com.example.m450.lb1.domain.airline;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlineRepository extends ListCrudRepository<Airline, Long> {
    // Built-in methods from ListCrudRepository:
    // List<Airline> findAll()
    // List<Airline> findAllById(Iterable<Long> ids)
    // <S extends Airline> List<S> saveAll(Iterable<S> entities)

    // Custom query methods
    Optional<Airline> findByIcao(String icao);

    Optional<Airline> findByName(String name);

    List<Airline> findByCountry(String country);

    boolean existsByIcao(String icao);

    boolean existsByName(String name);
}