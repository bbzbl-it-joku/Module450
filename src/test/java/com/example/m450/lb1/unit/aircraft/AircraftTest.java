package com.example.m450.lb1.unit.aircraft;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.m450.lb1.domain.BaseEntity;
import com.example.m450.lb1.domain.aircraft.Aircraft;

@DisplayName("Aircraft Entity Tests")
class AircraftTest {

    @Test
    @DisplayName("Should create aircraft using no-args constructor")
    void shouldCreateAircraftUsingNoArgsConstructor() {
        Aircraft aircraft = new Aircraft();

        assertNotNull(aircraft);
        assertNull(aircraft.getId());
        assertNull(aircraft.getRegistrationCode());
        assertNull(aircraft.getRegistrationPrefix());
        assertNull(aircraft.getAirlineId());
        assertNull(aircraft.getType());
        assertEquals(0, aircraft.getCapacity());
        assertNull(aircraft.getCreatedAt());
        assertNull(aircraft.getUpdatedAt());
    }

    @Test
    @DisplayName("Should create aircraft using builder")
    void shouldCreateAircraftUsingBuilder() {
        LocalDateTime now = LocalDateTime.now();
        Aircraft aircraft = Aircraft.builder()
                .id(1L)
                .registrationCode("HB-JDB")
                .registrationPrefix("HB")
                .airlineId(1L)
                .type("A320")
                .capacity(180)
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertNotNull(aircraft);
        assertEquals(1L, aircraft.getId());
        assertEquals("HB-JDB", aircraft.getRegistrationCode());
        assertEquals("HB", aircraft.getRegistrationPrefix());
        assertEquals(1L, aircraft.getAirlineId());
        assertEquals("A320", aircraft.getType());
        assertEquals(180, aircraft.getCapacity());
        assertEquals(now, aircraft.getCreatedAt());
        assertEquals(now, aircraft.getUpdatedAt());
    }

    @Test
    @DisplayName("Should create aircraft using all-args constructor")
    void shouldCreateAircraftUsingAllArgsConstructor() {
        Aircraft aircraft = new Aircraft("HB-JDB", "HB", 1L, "A320", 180);

        assertNotNull(aircraft);
        assertEquals("HB-JDB", aircraft.getRegistrationCode());
        assertEquals("HB", aircraft.getRegistrationPrefix());
        assertEquals(1L, aircraft.getAirlineId());
        assertEquals("A320", aircraft.getType());
        assertEquals(180, aircraft.getCapacity());
    }

    @Test
    @DisplayName("Should get and set all fields correctly")
    void shouldGetAndSetAllFields() {
        LocalDateTime now = LocalDateTime.now();
        Aircraft aircraft = new Aircraft();

        aircraft.setId(1L);
        aircraft.setRegistrationCode("HB-JDB");
        aircraft.setRegistrationPrefix("HB");
        aircraft.setAirlineId(1L);
        aircraft.setType("A320");
        aircraft.setCapacity(180);
        aircraft.setCreatedAt(now);
        aircraft.setUpdatedAt(now);

        assertEquals(1L, aircraft.getId());
        assertEquals("HB-JDB", aircraft.getRegistrationCode());
        assertEquals("HB", aircraft.getRegistrationPrefix());
        assertEquals(1L, aircraft.getAirlineId());
        assertEquals("A320", aircraft.getType());
        assertEquals(180, aircraft.getCapacity());
        assertEquals(now, aircraft.getCreatedAt());
        assertEquals(now, aircraft.getUpdatedAt());
    }

    @Test
    @DisplayName("Should be equal when same data")
    void shouldBeEqualWhenSameData() {
        LocalDateTime now = LocalDateTime.now();
        Aircraft aircraft1 = Aircraft.builder()
                .id(1L)
                .registrationCode("HB-JDB")
                .registrationPrefix("HB")
                .airlineId(1L)
                .type("A320")
                .capacity(180)
                .createdAt(now)
                .updatedAt(now)
                .build();

        Aircraft aircraft2 = Aircraft.builder()
                .id(1L)
                .registrationCode("HB-JDB")
                .registrationPrefix("HB")
                .airlineId(1L)
                .type("A320")
                .capacity(180)
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertEquals(aircraft1, aircraft2);
        assertEquals(aircraft1.hashCode(), aircraft2.hashCode());
    }

    @Test
    @DisplayName("Should not be equal when different data")
    void shouldNotBeEqualWhenDifferentData() {
        Aircraft aircraft1 = Aircraft.builder()
                .registrationCode("HB-JDB")
                .build();

        Aircraft aircraft2 = Aircraft.builder()
                .registrationCode("HB-JDC")
                .build();

        assertNotEquals(aircraft1, aircraft2);
        assertNotEquals(aircraft1.hashCode(), aircraft2.hashCode());
    }

    @Test
    @DisplayName("Should include all fields in toString")
    void shouldIncludeAllFieldsInToString() {
        LocalDateTime now = LocalDateTime.now();
        Aircraft aircraft = Aircraft.builder()
                .id(1L)
                .registrationCode("HB-JDB")
                .registrationPrefix("HB")
                .airlineId(1L)
                .type("A320")
                .capacity(180)
                .createdAt(now)
                .updatedAt(now)
                .build();

        String toString = aircraft.toString();

        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("registrationCode=HB-JDB"));
        assertTrue(toString.contains("registrationPrefix=HB"));
        assertTrue(toString.contains("airlineId=1"));
        assertTrue(toString.contains("type=A320"));
        assertTrue(toString.contains("capacity=180"));
        assertTrue(toString.contains("createdAt="));
        assertTrue(toString.contains("updatedAt="));
    }

    @Test
    @DisplayName("Should extend BaseEntity")
    void shouldExtendBaseEntity() {
        Aircraft aircraft = new Aircraft();
        assertTrue(aircraft instanceof BaseEntity);
    }

    @Test
    @DisplayName("Should handle null values in builder")
    void shouldHandleNullValuesInBuilder() {
        Aircraft aircraft = Aircraft.builder().build();

        assertNotNull(aircraft);
        assertNull(aircraft.getId());
        assertNull(aircraft.getRegistrationCode());
        assertNull(aircraft.getRegistrationPrefix());
        assertNull(aircraft.getAirlineId());
        assertNull(aircraft.getType());
        assertEquals(0, aircraft.getCapacity());
        assertNull(aircraft.getCreatedAt());
        assertNull(aircraft.getUpdatedAt());
    }

    @Test
    @DisplayName("Should be equal to itself")
    void shouldBeEqualToItself() {
        Aircraft aircraft = Aircraft.builder()
                .registrationCode("HB-JDB")
                .build();

        assertEquals(aircraft, aircraft);
        assertEquals(aircraft.hashCode(), aircraft.hashCode());
    }

    @Test
    @DisplayName("Should not be equal to null")
    void shouldNotBeEqualToNull() {
        Aircraft aircraft = Aircraft.builder()
                .registrationCode("HB-JDB")
                .build();

        assertNotEquals(null, aircraft);
    }

    @Test
    @DisplayName("Should not be equal to different type")
    void shouldNotBeEqualToDifferentType() {
        Aircraft aircraft = Aircraft.builder()
                .registrationCode("HB-JDB")
                .build();

        assertNotEquals(aircraft, new Object());
    }
}