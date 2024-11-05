package com.example.m450.lb1.domain.passenger;

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
public class PassengerService {
    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    public List<PassengerDTO.PassengerResponse> getAllPassengers() {
        return passengerRepository.findAll().stream()
                .map(passengerMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PassengerDTO.PassengerResponse createPassenger(PassengerDTO.PassengerRequest request)
            throws ResourceAlreadyExistsException {
        if (passengerRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Passenger", "email", request.getEmail());
        }

        Passenger passenger = passengerMapper.toEntity(request);
        Passenger savedPassenger = passengerRepository.save(passenger);
        return passengerMapper.toDTO(savedPassenger);
    }

    public PassengerDTO.PassengerResponse updatePassenger(Long id, PassengerDTO.PassengerRequest request)
            throws ResourceNotFoundException {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger", "id", id));
        passenger.setFirstName(request.getFirstName());
        passenger.setLastName(request.getLastName());
        passenger.setEmail(request.getEmail());
        passenger.setAddress(request.getAddress());
        Passenger updatedPassenger = passengerRepository.save(passenger);
        return passengerMapper.toDTO(updatedPassenger);
    }

    public void deletePassenger(Long id) throws ResourceNotFoundException {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger", "id", id));
        passengerRepository.delete(passenger);
    }

    public PassengerDTO.PassengerResponse getPassengerById(Long id) throws ResourceNotFoundException {
        return passengerRepository.findById(id)
                .map(passengerMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger", "id", id));
    }

    public List<PassengerDTO.PassengerResponse> getPassengersByFirstName(String firstName) {
        return passengerRepository.findByFirstName(firstName).stream()
                .map(passengerMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<PassengerDTO.PassengerResponse> getPassengersByLastName(String lastName) {
        return passengerRepository.findByLastName(lastName).stream()
                .map(passengerMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PassengerDTO.PassengerResponse getPassengerByEmail(String email) throws ResourceNotFoundException {
        return passengerRepository.findByEmail(email)
                .map(passengerMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger", "email", email));
    }

    public List<PassengerDTO.PassengerResponse> getPassengersByAddress(String address) {
        return passengerRepository.findByAddress(address).stream()
                .map(passengerMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<PassengerDTO.PassengerResponse> getPassengersByFirstNameAndLastName(String firstName, String lastName) {
        return passengerRepository.findByFirstNameAndLastName(firstName, lastName).stream()
                .map(passengerMapper::toDTO)
                .collect(Collectors.toList());
    }

}
