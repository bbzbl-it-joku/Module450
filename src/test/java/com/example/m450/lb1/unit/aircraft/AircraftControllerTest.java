package com.example.m450.lb1.unit.aircraft;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.m450.lb1.domain.aircraft.AircraftController;
import com.example.m450.lb1.domain.aircraft.AircraftDTO;
import com.example.m450.lb1.domain.aircraft.AircraftService;
import com.example.m450.lb1.exception.ResourceAlreadyExistsException;
import com.example.m450.lb1.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@WebMvcTest(AircraftController.class)
class AircraftControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AircraftService aircraftService;

    private ObjectMapper objectMapper;
    private AircraftDTO.AircraftRequest request;
    private AircraftDTO.AircraftResponse response;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

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
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("GET /api/aircraft - Should return all aircraft")
    void shouldGetAllAircraft() throws Exception {
        List<AircraftDTO.AircraftResponse> responses = Arrays.asList(response);
        when(aircraftService.getAllAircraft()).thenReturn(responses);

        mockMvc.perform(get("/api/aircraft"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(response.getId()))
                .andExpect(jsonPath("$[0].registrationCode").value(response.getRegistrationCode()));
    }

    @Test
    @DisplayName("GET /api/aircraft/{id} - Should return aircraft by id")
    void shouldGetAircraftById() throws Exception {
        when(aircraftService.getAircraftById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/aircraft/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.registrationCode").value(response.getRegistrationCode()));
    }

    @Test
    @DisplayName("GET /api/aircraft/{id} - Should return 404 when aircraft not found")
    void shouldReturn404WhenAircraftNotFound() throws Exception {
        when(aircraftService.getAircraftById(1L))
                .thenThrow(new ResourceNotFoundException("Aircraft", "id", 1L));

        mockMvc.perform(get("/api/aircraft/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/aircraft - Should create new aircraft")
    void shouldCreateNewAircraft() throws Exception {
        when(aircraftService.createAircraft(any(AircraftDTO.AircraftRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/aircraft")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.registrationCode").value(response.getRegistrationCode()));
    }

    @Test
    @DisplayName("POST /api/aircraft - Should return 409 when aircraft already exists")
    void shouldReturn409WhenAircraftExists() throws Exception {
        when(aircraftService.createAircraft(any(AircraftDTO.AircraftRequest.class)))
                .thenThrow(new ResourceAlreadyExistsException("Aircraft", "registrationCode", "ABC123"));

        mockMvc.perform(post("/api/aircraft")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("PUT /api/aircraft/{id} - Should update aircraft")
    void shouldUpdateAircraft() throws Exception {
        when(aircraftService.updateAircraft(eq(1L), any(AircraftDTO.AircraftRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/aircraft/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.registrationCode").value(response.getRegistrationCode()));
    }

    @Test
    @DisplayName("DELETE /api/aircraft/{id} - Should delete aircraft")
    void shouldDeleteAircraft() throws Exception {
        doNothing().when(aircraftService).deleteAircraft(1L);

        mockMvc.perform(delete("/api/aircraft/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /api/aircraft/registrationCode/{code} - Should return aircraft by registration code")
    void shouldGetAircraftByRegistrationCode() throws Exception {
        when(aircraftService.getAircraftByRegistrationCode("ABC123")).thenReturn(response);

        mockMvc.perform(get("/api/aircraft/registrationCode/ABC123"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.registrationCode").value("ABC123"));
    }

    @Test
    @DisplayName("GET /api/aircraft/airline/{airlineId} - Should return aircraft by airline")
    void shouldGetAircraftByAirline() throws Exception {
        List<AircraftDTO.AircraftResponse> responses = Arrays.asList(response);
        when(aircraftService.getAircraftByAirline(1L)).thenReturn(responses);

        mockMvc.perform(get("/api/aircraft/airline/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].airlineId").value(1L));
    }

    @Test
    @DisplayName("GET /api/aircraft/type/{type} - Should return aircraft by type")
    void shouldGetAircraftByType() throws Exception {
        List<AircraftDTO.AircraftResponse> responses = Arrays.asList(response);
        when(aircraftService.getAircraftByType("Boeing 737")).thenReturn(responses);

        mockMvc.perform(get("/api/aircraft/type/Boeing 737"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].type").value("Boeing 737"));
    }

    @Test
    @DisplayName("GET /api/aircraft/capacity/{capacity} - Should return aircraft by capacity")
    void shouldGetAircraftByCapacity() throws Exception {
        List<AircraftDTO.AircraftResponse> responses = Arrays.asList(response);
        when(aircraftService.getAircraftByCapacity(150)).thenReturn(responses);

        mockMvc.perform(get("/api/aircraft/capacity/150"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].capacity").value(150));
    }

    @Test
    @DisplayName("GET /api/aircraft/capacity/{min}/{max} - Should return aircraft by capacity range")
    void shouldGetAircraftByCapacityRange() throws Exception {
        List<AircraftDTO.AircraftResponse> responses = Arrays.asList(response);
        when(aircraftService.getAircraftByCapacityRange(100, 200)).thenReturn(responses);

        mockMvc.perform(get("/api/aircraft/capacity/100/200"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].capacity").value(150));
    }

    @Test
    @DisplayName("POST /api/aircraft - Should return 400 for invalid request")
    void shouldReturn400ForInvalidRequest() throws Exception {
        request.setCapacity(-1); // Invalid capacity

        mockMvc.perform(post("/api/aircraft")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/aircraft/airline/{airlineId}/type/{type} - Should return aircraft by airline and type")
    void shouldGetAircraftByAirlineAndType() throws Exception {
        List<AircraftDTO.AircraftResponse> responses = Arrays.asList(response);
        when(aircraftService.getAircraftByAirlineAndType(1L, "Boeing 737")).thenReturn(responses);

        mockMvc.perform(get("/api/aircraft/airline/1/type/Boeing 737"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].airlineId").value(1L))
                .andExpect(jsonPath("$[0].type").value("Boeing 737"));
    }
}