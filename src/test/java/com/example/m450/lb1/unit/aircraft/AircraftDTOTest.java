package com.example.m450.lb1.unit.aircraft;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.m450.lb1.domain.aircraft.AircraftDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class AircraftDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Should create valid AircraftRequest")
    void shouldCreateValidAircraftRequest() {
        AircraftDTO.AircraftRequest request = AircraftDTO.AircraftRequest.builder()
                .registrationCode("ABC123")
                .registrationPrefix("HB")
                .airlineId(1L)
                .type("Boeing 737")
                .capacity(150)
                .build();

        Set<ConstraintViolation<AircraftDTO.AircraftRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Should fail validation when required fields are missing")
    void shouldFailValidationWhenFieldsAreMissing() {
        AircraftDTO.AircraftRequest request = new AircraftDTO.AircraftRequest();

        Set<ConstraintViolation<AircraftDTO.AircraftRequest>> violations = validator.validate(request);
        assertEquals(5, violations.size());
        
        assertTrue(violations.stream()
                .map(ConstraintViolation::getMessage)
                .anyMatch(message -> message.equals("Registration code is required")));
        assertTrue(violations.stream()
                .map(ConstraintViolation::getMessage)
                .anyMatch(message -> message.equals("Registration prefix is required")));
        assertTrue(violations.stream()
                .map(ConstraintViolation::getMessage)
                .anyMatch(message -> message.equals("Airline ID is required")));
        assertTrue(violations.stream()
                .map(ConstraintViolation::getMessage)
                .anyMatch(message -> message.equals("Aircraft type is required")));
        assertTrue(violations.stream()
                .map(ConstraintViolation::getMessage)
                .anyMatch(message -> message.equals("Capacity is required")));
    }

    @Test
    @DisplayName("Should fail validation when capacity is less than 1")
    void shouldFailValidationWhenCapacityIsInvalid() {
        AircraftDTO.AircraftRequest request = AircraftDTO.AircraftRequest.builder()
                .registrationCode("ABC123")
                .registrationPrefix("HB")
                .airlineId(1L)
                .type("Boeing 737")
                .capacity(0)
                .build();

        Set<ConstraintViolation<AircraftDTO.AircraftRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        assertEquals("Capacity must be greater than 0", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Should create valid AircraftResponse")
    void shouldCreateValidAircraftResponse() {
        LocalDateTime now = LocalDateTime.now();
        
        AircraftDTO.AircraftResponse response = AircraftDTO.AircraftResponse.builder()
                .id(1L)
                .registrationCode("ABC123")
                .registrationPrefix("HB")
                .airlineId(1L)
                .type("Boeing 737")
                .capacity(150)
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("ABC123", response.getRegistrationCode());
        assertEquals("HB", response.getRegistrationPrefix());
        assertEquals(1L, response.getAirlineId());
        assertEquals("Boeing 737", response.getType());
        assertEquals(150, response.getCapacity());
        assertEquals(now, response.getCreatedAt());
        assertEquals(now, response.getUpdatedAt());
    }

    @Test
    @DisplayName("Should create AircraftResponse using no-args constructor")
    void shouldCreateAircraftResponseUsingNoArgsConstructor() {
        AircraftDTO.AircraftResponse response = new AircraftDTO.AircraftResponse();
        
        assertNotNull(response);
        assertNull(response.getId());
        assertNull(response.getRegistrationCode());
        assertNull(response.getRegistrationPrefix());
        assertNull(response.getAirlineId());
        assertNull(response.getType());
        assertEquals(0, response.getCapacity());
        assertNull(response.getCreatedAt());
        assertNull(response.getUpdatedAt());
    }

    @Test
    @DisplayName("Should properly use builder pattern for AircraftRequest")
    void shouldUseBuilderPatternForAircraftRequest() {
        AircraftDTO.AircraftRequest request = AircraftDTO.AircraftRequest.builder()
                .registrationCode("ABC123")
                .registrationPrefix("HB")
                .airlineId(1L)
                .type("Boeing 737")
                .capacity(150)
                .build();

        assertNotNull(request);
        assertEquals("ABC123", request.getRegistrationCode());
        assertEquals("HB", request.getRegistrationPrefix());
        assertEquals(1L, request.getAirlineId());
        assertEquals("Boeing 737", request.getType());
        assertEquals(150, request.getCapacity());
    }
}