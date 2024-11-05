package com.example.m450.lb1.domain.passenger;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends ListCrudRepository<Passenger, Long> {
    // Built-in methods from ListCrudRepository:
    // List<Passenger> findAll()
    // List<Passenger> findAllById(Iterable<Long> ids)
    // <S extends Passenger> List<S> saveAll(Iterable<S> entities)

    // Custom query methods
    List<Passenger> findByFirstName(String firstName);

    List<Passenger> findByLastName(String lastName);

    Optional<Passenger> findByEmail(String email);

    List<Passenger> findByAddress(String address);

    List<Passenger> findByFirstNameAndLastName(String firstName, String lastName);

    boolean existsByEmail(String email);
}
