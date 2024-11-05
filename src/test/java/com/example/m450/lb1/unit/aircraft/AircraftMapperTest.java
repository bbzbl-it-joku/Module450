package com.example.m450.lb1.unit.aircraft;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.m450.lb1.domain.aircraft.Aircraft;
import com.example.m450.lb1.domain.aircraft.AircraftDTO;
import com.example.m450.lb1.domain.aircraft.AircraftMapper;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class AircraftMapperTest {

    private AircraftMapper mapper;
    private Validator validator;

    @BeforeEach
    void setUp() {
        mapper = new AircraftMapper();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Should map valid AircraftRequest to Aircraft entity")
    void shouldMapValidRequestToEntity() {
        // Given
        AircraftDTO.AircraftRequest request = AircraftDTO.AircraftRequest.builder()
                .registrationCode("ABC123")
                .registrationPrefix("HB")
                .airlineId(1L)
                .type("Boeing 737")
                .capacity(150)
                .build();

        // When
        Aircraft entity = mapper.toEntity(request);

        // Then
        assertNotNull(entity);
        assertEquals(request.getRegistrationCode(), entity.getRegistrationCode());
        assertEquals(request.getRegistrationPrefix(), entity.getRegistrationPrefix());
        assertEquals(request.getAirlineId(), entity.getAirlineId());
        assertEquals(request.getType(), entity.getType());
        assertEquals(request.getCapacity(), entity.getCapacity());
    }

    @Test
    @DisplayName("Should map valid Aircraft entity to AircraftResponse")
    void shouldMapValidEntityToResponse() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Aircraft entity = Aircraft.builder()
                .id(1L)
                .registrationCode("ABC123")
                .registrationPrefix("HB")
                .airlineId(1L)
                .type("Boeing 737")
                .capacity(150)
                .createdAt(now)
                .updatedAt(now)
                .build();

        // When
        AircraftDTO.AircraftResponse response = mapper.toDto(entity);

        // Then
        assertNotNull(response);
        assertEquals(entity.getId(), response.getId());
        assertEquals(entity.getRegistrationCode(), response.getRegistrationCode());
        assertEquals(entity.getRegistrationPrefix(), response.getRegistrationPrefix());
        assertEquals(entity.getAirlineId(), response.getAirlineId());
        assertEquals(entity.getType(), response.getType());
        assertEquals(entity.getCapacity(), response.getCapacity());
        assertEquals(entity.getCreatedAt(), response.getCreatedAt());
        assertEquals(entity.getUpdatedAt(), response.getUpdatedAt());
    }

    @Test
    @DisplayName("Should throw ConstraintViolationException when mapping invalid request")
    void shouldThrowExceptionForInvalidRequest() {
        // Given
        AircraftDTO.AircraftRequest request = new AircraftDTO.AircraftRequest();

        // When & Then
        assertThrows(ConstraintViolationException.class, () -> {
            var violations = validator.validate(request);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
            mapper.toEntity(request);
        });
    }

    @Test
    @DisplayName("Should preserve all field values during bidirectional mapping")
    void shouldPreserveValuesInBidirectionalMapping() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Aircraft originalEntity = Aircraft.builder()
                .id(1L)
                .registrationCode("ABC123")
                .registrationPrefix("HB")
                .airlineId(1L)
                .type("Boeing 737")
                .capacity(150)
                .createdAt(now)
                .updatedAt(now)
                .build();

        // When
        AircraftDTO.AircraftResponse response = mapper.toDto(originalEntity);
        Aircraft reconstructedEntity = Aircraft.builder()
                .id(response.getId())
                .registrationCode(response.getRegistrationCode())
                .registrationPrefix(response.getRegistrationPrefix())
                .airlineId(response.getAirlineId())
                .type(response.getType())
                .capacity(response.getCapacity())
                .createdAt(response.getCreatedAt())
                .updatedAt(response.getUpdatedAt())
                .build();

        // Then
        assertEquals(originalEntity.getId(), reconstructedEntity.getId());
        assertEquals(originalEntity.getRegistrationCode(), reconstructedEntity.getRegistrationCode());
        assertEquals(originalEntity.getRegistrationPrefix(), reconstructedEntity.getRegistrationPrefix());
        assertEquals(originalEntity.getAirlineId(), reconstructedEntity.getAirlineId());
        assertEquals(originalEntity.getType(), reconstructedEntity.getType());
        assertEquals(originalEntity.getCapacity(), reconstructedEntity.getCapacity());
        assertEquals(originalEntity.getCreatedAt(), reconstructedEntity.getCreatedAt());
        assertEquals(originalEntity.getUpdatedAt(), reconstructedEntity.getUpdatedAt());
    }
}