package com.example.m450.lb1.integration.aircraft;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.m450.lb1.domain.aircraft.Aircraft;
import com.example.m450.lb1.domain.aircraft.AircraftDTO;
import com.example.m450.lb1.domain.aircraft.AircraftMapper;
import com.example.m450.lb1.domain.aircraft.AircraftRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AircraftIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AircraftRepository aircraftRepository;

    private AircraftMapper aircraftMapper;

    private ObjectMapper objectMapper;
    private AircraftDTO.AircraftRequest request;

    @BeforeEach
    void setUp() {
        aircraftMapper = new AircraftMapper();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        request = AircraftDTO.AircraftRequest.builder()
                .registrationCode("ABC123")
                .registrationPrefix("HB")
                .airlineId(1L)
                .type("Boeing 737")
                .capacity(150)
                .build();
    }

    @Test
    @DisplayName("GET /api/aircraft - Should return all aircraft")
    void shouldGetAllAircraft() throws Exception {
        AircraftDTO.AircraftResponse response = saveTestAircraft();

        mockMvc.perform(get("/api/aircraft"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(response.getId()))
                .andExpect(jsonPath("$[0].registrationCode").value(response.getRegistrationCode()));
    }

    @Test
    @DisplayName("GET /api/aircraft/{id} - Should return aircraft by id")
    void shouldGetAircraftById() throws Exception {
        AircraftDTO.AircraftResponse response = saveTestAircraft();

        mockMvc.perform(get("/api/aircraft/" + response.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.registrationCode").value(response.getRegistrationCode()));
    }

    @Test
    @DisplayName("GET /api/aircraft/{id} - Should return 404 when aircraft not found")
    void shouldReturn404WhenAircraftNotFound() throws Exception {
        mockMvc.perform(get("/api/aircraft/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/aircraft - Should create new aircraft")
    void shouldCreateNewAircraft() throws Exception {
        mockMvc.perform(post("/api/aircraft")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.registrationCode").value(request.getRegistrationCode()));
    }

    @Test
    @DisplayName("PUT /api/aircraft/{id} - Should update aircraft")
    void shouldUpdateAircraft() throws Exception {
        AircraftDTO.AircraftResponse response = saveTestAircraft();
        request.setType("Airbus A320");

        mockMvc.perform(put("/api/aircraft/" + response.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("Airbus A320"));
    }

    @Test
    @DisplayName("DELETE /api/aircraft/{id} - Should delete aircraft")
    void shouldDeleteAircraft() throws Exception {
        AircraftDTO.AircraftResponse response = saveTestAircraft();

        mockMvc.perform(delete("/api/aircraft/" + response.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /api/aircraft/registrationCode/{code} - Should return aircraft by registration code")
    void shouldGetAircraftByRegistrationCode() throws Exception {
        AircraftDTO.AircraftResponse response = saveTestAircraft();

        mockMvc.perform(get("/api/aircraft/registrationCode/" + response.getRegistrationCode()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.registrationCode").value(response.getRegistrationCode()));
    }

    @Test
    @DisplayName("GET /api/aircraft/airline/{airlineId} - Should return aircraft by airline")
    void shouldGetAircraftByAirline() throws Exception {
        AircraftDTO.AircraftResponse response = saveTestAircraft();

        mockMvc.perform(get("/api/aircraft/airline/" + response.getAirlineId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].airlineId").value(response.getAirlineId()));
    }

    @Test
    @DisplayName("GET /api/aircraft/type/{type} - Should return aircraft by type")
    void shouldGetAircraftByType() throws Exception {
        AircraftDTO.AircraftResponse response = saveTestAircraft();

        mockMvc.perform(get("/api/aircraft/type/" + response.getType()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value(response.getType()));
    }

    @Test
    @DisplayName("GET /api/aircraft/capacity/{capacity} - Should return aircraft by capacity")
    void shouldGetAircraftByCapacity() throws Exception {
        AircraftDTO.AircraftResponse response = saveTestAircraft();

        mockMvc.perform(get("/api/aircraft/capacity/" + response.getCapacity()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].capacity").value(response.getCapacity()));
    }

    @Test
    @DisplayName("GET /api/aircraft/capacity/{min}/{max} - Should return aircraft by capacity range")
    void shouldGetAircraftByCapacityRange() throws Exception {
        AircraftDTO.AircraftResponse response = saveTestAircraft();

        mockMvc.perform(get("/api/aircraft/capacity/100/200"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].capacity").value(response.getCapacity()));
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
        AircraftDTO.AircraftResponse response = saveTestAircraft();

        mockMvc.perform(get("/api/aircraft/airline/" + response.getAirlineId() + "/type/" + response.getType()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].airlineId").value(response.getAirlineId()))
                .andExpect(jsonPath("$[0].type").value(response.getType()));
    }

    private AircraftDTO.AircraftResponse saveTestAircraft() {
        Aircraft aircraft = aircraftRepository.save(Aircraft.builder()
                .registrationCode(request.getRegistrationCode())
                .registrationPrefix(request.getRegistrationPrefix())
                .airlineId(request.getAirlineId())
                .type(request.getType())
                .capacity(request.getCapacity())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());
        return aircraftMapper.toDto(aircraft);
    }
}
