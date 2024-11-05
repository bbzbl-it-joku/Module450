package com.example.m450.lb1.domain.aircraft;

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
public class AircraftService {
    private final AircraftRepository aircraftRepository;
    private final AircraftMapper aircraftMapper;

    public List<AircraftDTO.AircraftResponse> getAllAircraft() {
        return aircraftRepository.findAll().stream()
                .map(aircraftMapper::toDto)
                .collect(Collectors.toList());
    }

    public AircraftDTO.AircraftResponse createAircraft(@Valid AircraftDTO.AircraftRequest request)
            throws ResourceAlreadyExistsException {
        if (aircraftRepository.existsByRegistrationCode(request.getRegistrationCode())) {
            throw new ResourceAlreadyExistsException("Aircraft", "registrationCode", request.getRegistrationCode());
        }
        Aircraft aircraft = aircraftMapper.toEntity(request);
        Aircraft savedAircraft = aircraftRepository.save(aircraft);
        return aircraftMapper.toDto(savedAircraft);
    }

    public AircraftDTO.AircraftResponse getAircraftById(Long id) throws ResourceNotFoundException {
        return aircraftRepository.findById(id)
                .map(aircraftMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft", "id", id));
    }

    public AircraftDTO.AircraftResponse getAircraftByRegistrationCode(String registrationCode)
            throws ResourceNotFoundException {
        return aircraftRepository.findByRegistrationCode(registrationCode)
                .map(aircraftMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft", "registrationCode", registrationCode));
    }

    public List<AircraftDTO.AircraftResponse> getAircraftByRegistrationPrefix(String registrationPrefix) {
        List<Aircraft> aircraft = aircraftRepository.findByRegistrationPrefix(registrationPrefix);
        return aircraft.stream()
                .map(aircraftMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<AircraftDTO.AircraftResponse> getAircraftByAirline(Long airlineId) throws ResourceNotFoundException {
        List<Aircraft> aircraft = aircraftRepository.findByAirlineId(airlineId);
        if (aircraft.isEmpty()) {
            throw new ResourceNotFoundException("Aircraft", "airlineId", airlineId);
        }
        return aircraft.stream()
                .map(aircraftMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<AircraftDTO.AircraftResponse> getAircraftByType(String type) throws ResourceNotFoundException {
        List<Aircraft> aircraft = aircraftRepository.findByType(type);
        if (aircraft.isEmpty()) {
            throw new ResourceNotFoundException("Aircraft", "type", type);
        }
        return aircraft.stream()
                .map(aircraftMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<AircraftDTO.AircraftResponse> getAircraftByCapacity(int capacity)
            throws ResourceNotFoundException {
        List<Aircraft> aircraft = aircraftRepository.findByCapacityGreaterThan(capacity);
        if (aircraft.isEmpty()) {
            throw new ResourceNotFoundException("Aircraft", "capacity", ">" + capacity);
        }
        return aircraft.stream()
                .map(aircraftMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<AircraftDTO.AircraftResponse> getAircraftByCapacityRange(int minCapacity, int maxCapacity)
            throws ResourceNotFoundException {
        List<Aircraft> aircraft = aircraftRepository.findByCapacityBetween(minCapacity, maxCapacity);
        if (aircraft.isEmpty()) {
            throw new ResourceNotFoundException("Aircraft", "capacity", minCapacity + "-" + maxCapacity);
        }
        return aircraft.stream()
                .map(aircraftMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<AircraftDTO.AircraftResponse> getAircraftByAirlineAndType(Long airlineId, String type)
            throws ResourceNotFoundException {
        List<Aircraft> aircraft = aircraftRepository.findByAirlineIdAndType(airlineId, type);
        if (aircraft.isEmpty()) {
            throw new ResourceNotFoundException("Aircraft", "airlineId and type", airlineId + " and " + type);
        }
        return aircraft.stream()
                .map(aircraftMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteAircraft(Long id) throws ResourceNotFoundException {
        if (!aircraftRepository.existsById(id)) {
            throw new ResourceNotFoundException("Aircraft", "id", id);
        }
        aircraftRepository.deleteById(id);
    }

    public AircraftDTO.AircraftResponse updateAircraft(Long id, @Valid AircraftDTO.AircraftRequest request)
            throws ResourceNotFoundException {
        Aircraft existingAircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft", "id", id));

        existingAircraft.setAirlineId(request.getAirlineId());
        existingAircraft.setType(request.getType());
        existingAircraft.setCapacity(request.getCapacity());

        Aircraft updatedAircraft = aircraftRepository.save(existingAircraft);
        return aircraftMapper.toDto(updatedAircraft);
    }
}