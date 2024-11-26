package com.example.m450.lb1.domain.aircraft;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AircraftRepository extends ListCrudRepository<Aircraft, Long> {
    // Built-in methods from ListCrudRepository:
    // List<Aircraft> findAll()
    // List<Aircraft> findAllById(Iterable<Long> ids)
    // <S extends Aircraft> List<S> saveAll(Iterable<S> entities)

    // Custom query methods
    Optional<Aircraft> findByRegistrationCode(String registrationCode);

    List<Aircraft> findByRegistrationPrefix(String registrationPrefix);

    List<Aircraft> findByAirlineId(Long airlineId);

    List<Aircraft> findByType(String type);

    List<Aircraft> findByCapacity(int capacity);

    List<Aircraft> findByCapacityGreaterThan(int capacity);

    List<Aircraft> findByCapacityBetween(int minCapacity, int maxCapacity);

    List<Aircraft> findByAirlineIdAndType(Long airlineId, String type);

    boolean existsByRegistrationCode(String registrationCode);
}