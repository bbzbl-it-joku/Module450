package com.example.m450.lb1.unit.aircraft;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.m450.lb1.domain.aircraft.Aircraft;
import com.example.m450.lb1.domain.aircraft.AircraftDTO;
import com.example.m450.lb1.domain.aircraft.AircraftMapper;
import com.example.m450.lb1.domain.aircraft.AircraftRepository;
import com.example.m450.lb1.domain.aircraft.AircraftService;
import com.example.m450.lb1.exception.ResourceAlreadyExistsException;
import com.example.m450.lb1.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class AircraftServiceTest {

    @Mock
    private AircraftRepository aircraftRepository;

    @Mock
    private AircraftMapper aircraftMapper;

    @InjectMocks
    private AircraftService aircraftService;

    private Aircraft aircraft;
    private AircraftDTO.AircraftRequest request;
    private AircraftDTO.AircraftResponse response;

    @BeforeEach
    void setUp() {
        aircraft = Aircraft.builder()
                .id(1L)
                .registrationCode("ABC123")
                .registrationPrefix("HB")
                .airlineId(1L)
                .type("Boeing 737")
                .capacity(150)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        request = AircraftDTO.AircraftRequest.builder()
                .registrationCode("ABC123")
                .registrationPrefix("HB")
                .airlineId(1L)
                .type("Boeing 737")
                .capacity(150)
                .build();

        response = AircraftDTO.AircraftResponse.builder()
                .id(1L)
                .registrationCode("ABC123")
                .registrationPrefix("HB")
                .airlineId(1L)
                .type("Boeing 737")
                .capacity(150)
                .createdAt(aircraft.getCreatedAt())
                .updatedAt(aircraft.getUpdatedAt())
                .build();
    }

    @Test
    @DisplayName("Should get all aircraft")
    void shouldGetAllAircraft() {
        // Given
        when(aircraftRepository.findAll()).thenReturn(Arrays.asList(aircraft));
        when(aircraftMapper.toDto(aircraft)).thenReturn(response);

        // When
        List<AircraftDTO.AircraftResponse> result = aircraftService.getAllAircraft();

        // Then
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(response, result.get(0));
        verify(aircraftRepository).findAll();
        verify(aircraftMapper).toDto(aircraft);
    }

    @Test
    @DisplayName("Should create aircraft successfully")
    void shouldCreateAircraft() throws ResourceAlreadyExistsException {
        // Given
        when(aircraftRepository.existsByRegistrationCode(request.getRegistrationCode())).thenReturn(false);
        when(aircraftMapper.toEntity(request)).thenReturn(aircraft);
        when(aircraftRepository.save(aircraft)).thenReturn(aircraft);
        when(aircraftMapper.toDto(aircraft)).thenReturn(response);

        // When
        AircraftDTO.AircraftResponse result = aircraftService.createAircraft(request);

        // Then
        assertNotNull(result);
        assertEquals(response, result);
        verify(aircraftRepository).existsByRegistrationCode(request.getRegistrationCode());
        verify(aircraftMapper).toEntity(request);
        verify(aircraftRepository).save(aircraft);
        verify(aircraftMapper).toDto(aircraft);
    }

    @Test
    @DisplayName("Should throw ResourceAlreadyExistsException when creating duplicate aircraft")
    void shouldThrowExceptionWhenCreatingDuplicateAircraft() {
        // Given
        when(aircraftRepository.existsByRegistrationCode(request.getRegistrationCode())).thenReturn(true);

        // When & Then
        assertThrows(ResourceAlreadyExistsException.class, () -> 
            aircraftService.createAircraft(request)
        );
        verify(aircraftRepository).existsByRegistrationCode(request.getRegistrationCode());
        verify(aircraftMapper, never()).toEntity(any());
        verify(aircraftRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should get aircraft by ID")
    void shouldGetAircraftById() throws ResourceNotFoundException {
        // Given
        when(aircraftRepository.findById(1L)).thenReturn(Optional.of(aircraft));
        when(aircraftMapper.toDto(aircraft)).thenReturn(response);

        // When
        AircraftDTO.AircraftResponse result = aircraftService.getAircraftById(1L);

        // Then
        assertNotNull(result);
        assertEquals(response, result);
        verify(aircraftRepository).findById(1L);
        verify(aircraftMapper).toDto(aircraft);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when aircraft not found by ID")
    void shouldThrowExceptionWhenAircraftNotFoundById() {
        // Given
        when(aircraftRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> 
            aircraftService.getAircraftById(1L)
        );
        verify(aircraftRepository).findById(1L);
        verify(aircraftMapper, never()).toDto(any());
    }

    @Test
    @DisplayName("Should get aircraft by airline ID")
    void shouldGetAircraftByAirline() throws ResourceNotFoundException {
        // Given
        when(aircraftRepository.findByAirlineId(1L)).thenReturn(Arrays.asList(aircraft));
        when(aircraftMapper.toDto(aircraft)).thenReturn(response);

        // When
        List<AircraftDTO.AircraftResponse> result = aircraftService.getAircraftByAirline(1L);

        // Then
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(response, result.get(0));
        verify(aircraftRepository).findByAirlineId(1L);
        verify(aircraftMapper).toDto(aircraft);
    }

    @Test
    @DisplayName("Should update aircraft successfully")
    void shouldUpdateAircraft() throws ResourceNotFoundException {
        // Given
        Aircraft updatedAircraft = Aircraft.builder()
            .type("Boeing 747")
            .capacity(400)
            .build();
        
        when(aircraftRepository.findById(1L)).thenReturn(Optional.of(aircraft));
        when(aircraftRepository.save(any(Aircraft.class))).thenReturn(updatedAircraft);
        when(aircraftMapper.toDto(updatedAircraft)).thenReturn(response);

        // When
        AircraftDTO.AircraftResponse result = aircraftService.updateAircraft(1L, request);

        // Then
        assertNotNull(result);
        verify(aircraftRepository).findById(1L);
        verify(aircraftRepository).save(any(Aircraft.class));
        verify(aircraftMapper).toDto(any(Aircraft.class));
    }

    @Test
    @DisplayName("Should delete aircraft successfully")
    void shouldDeleteAircraft() throws ResourceNotFoundException {
        // Given
        when(aircraftRepository.existsById(1L)).thenReturn(true);
        doNothing().when(aircraftRepository).deleteById(1L);

        // When
        aircraftService.deleteAircraft(1L);

        // Then
        verify(aircraftRepository).existsById(1L);
        verify(aircraftRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when deleting non-existent aircraft")
    void shouldThrowExceptionWhenDeletingNonExistentAircraft() {
        // Given
        when(aircraftRepository.existsById(1L)).thenReturn(false);

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> 
            aircraftService.deleteAircraft(1L)
        );
        verify(aircraftRepository).existsById(1L);
        verify(aircraftRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Should get aircraft by type")
    void shouldGetAircraftByType() throws ResourceNotFoundException {
        // Given
        when(aircraftRepository.findByType("Boeing 737")).thenReturn(Arrays.asList(aircraft));
        when(aircraftMapper.toDto(aircraft)).thenReturn(response);

        // When
        List<AircraftDTO.AircraftResponse> result = aircraftService.getAircraftByType("Boeing 737");

        // Then
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(response, result.get(0));
        verify(aircraftRepository).findByType("Boeing 737");
        verify(aircraftMapper).toDto(aircraft);
    }

    @Test
    @DisplayName("Should get aircraft by capacity range")
    void shouldGetAircraftByCapacityRange() throws ResourceNotFoundException {
        // Given
        when(aircraftRepository.findByCapacityBetween(100, 200)).thenReturn(Arrays.asList(aircraft));
        when(aircraftMapper.toDto(aircraft)).thenReturn(response);

        // When
        List<AircraftDTO.AircraftResponse> result = aircraftService.getAircraftByCapacityRange(100, 200);

        // Then
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(response, result.get(0));
        verify(aircraftRepository).findByCapacityBetween(100, 200);
        verify(aircraftMapper).toDto(aircraft);
    }
}