package com.example.m450.lb1.integration.aircraft;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.m450.lb1.domain.aircraft.AircraftDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AircraftControllerTest {
    private final static String API_AIRCRAFT = "/api/aircraft";

    private final List<AircraftDTO.AircraftResponse> testAircraftResponses = Arrays.asList(
            AircraftDTO.AircraftResponse.builder().id(1L).registrationCode("HB-JCN").registrationPrefix("HB")
                    .airlineId(1L).type("Airbus A320neo").capacity(180).build(),
            AircraftDTO.AircraftResponse.builder().id(2L).registrationCode("HB-JCK").registrationPrefix("HB")
                    .airlineId(1L).type("Airbus A320neo").capacity(180).build(),
            AircraftDTO.AircraftResponse.builder().id(3L).registrationCode("D-AIEF").registrationPrefix("D")
                    .airlineId(2L).type("Airbus A320").capacity(168).build(),
            AircraftDTO.AircraftResponse.builder().id(4L).registrationCode("D-AIUL").registrationPrefix("D")
                    .airlineId(2L).type("Airbus A320neo").capacity(180).build(),
            AircraftDTO.AircraftResponse.builder().id(5L).registrationCode("OE-LBK").registrationPrefix("OE")
                    .airlineId(3L).type("Airbus A320").capacity(174).build(),
            AircraftDTO.AircraftResponse.builder().id(6L).registrationCode("G-UZHA").registrationPrefix("G")
                    .airlineId(4L).type("Airbus A320neo").capacity(186).build(),
            AircraftDTO.AircraftResponse.builder().id(7L).registrationCode("G-TTNA").registrationPrefix("G")
                    .airlineId(5L).type("Airbus A320neo").capacity(180).build());

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Flyway flyway;

    @BeforeEach
    @Transactional(propagation = Propagation.NEVER)
    void setUp() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @DisplayName("GET /api/aircraft - Should return all aircraft")
    void shouldGetAllAircraft() throws Exception {
        mockMvc.perform(get(API_AIRCRAFT))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(testAircraftResponses.size()))
                .andExpect(jsonPath("$[0].id").value(testAircraftResponses.get(0).getId()))
                .andExpect(jsonPath("$[0].registrationCode").value(testAircraftResponses.get(0).getRegistrationCode()))
                .andExpect(
                        jsonPath("$[0].registrationPrefix").value(testAircraftResponses.get(0).getRegistrationPrefix()))
                .andExpect(jsonPath("$[0].airlineId").value(testAircraftResponses.get(0).getAirlineId()))
                .andExpect(jsonPath("$[0].type").value(testAircraftResponses.get(0).getType()))
                .andExpect(jsonPath("$[0].capacity").value(testAircraftResponses.get(0).getCapacity()))
                .andExpect(jsonPath("$[0].createdAt").exists())
                .andExpect(jsonPath("$[0].updatedAt").hasJsonPath())
                .andExpect(jsonPath("$[1].id").value(testAircraftResponses.get(1).getId()))
                .andExpect(jsonPath("$[1].registrationCode").value(testAircraftResponses.get(1).getRegistrationCode()))
                .andExpect(
                        jsonPath("$[1].registrationPrefix").value(testAircraftResponses.get(1).getRegistrationPrefix()))
                .andExpect(jsonPath("$[1].airlineId").value(testAircraftResponses.get(1).getAirlineId()))
                .andExpect(jsonPath("$[1].type").value(testAircraftResponses.get(1).getType()))
                .andExpect(jsonPath("$[1].capacity").value(testAircraftResponses.get(1).getCapacity()))
                .andExpect(jsonPath("$[1].createdAt").exists())
                .andExpect(jsonPath("$[1].updatedAt").hasJsonPath())
                .andExpect(jsonPath("$[2].id").value(testAircraftResponses.get(2).getId()))
                .andExpect(jsonPath("$[2].registrationCode").value(testAircraftResponses.get(2).getRegistrationCode()))
                .andExpect(
                        jsonPath("$[2].registrationPrefix").value(testAircraftResponses.get(2).getRegistrationPrefix()))
                .andExpect(jsonPath("$[2].airlineId").value(testAircraftResponses.get(2).getAirlineId()))
                .andExpect(jsonPath("$[2].type").value(testAircraftResponses.get(2).getType()))
                .andExpect(jsonPath("$[2].capacity").value(testAircraftResponses.get(2).getCapacity()))
                .andExpect(jsonPath("$[2].createdAt").exists())
                .andExpect(jsonPath("$[2].updatedAt").hasJsonPath())
                .andExpect(jsonPath("$[3].id").value(testAircraftResponses.get(3).getId()))
                .andExpect(jsonPath("$[3].registrationCode").value(testAircraftResponses.get(3).getRegistrationCode()))
                .andExpect(
                        jsonPath("$[3].registrationPrefix").value(testAircraftResponses.get(3).getRegistrationPrefix()))
                .andExpect(jsonPath("$[3].airlineId").value(testAircraftResponses.get(3).getAirlineId()))
                .andExpect(jsonPath("$[3].type").value(testAircraftResponses.get(3).getType()))
                .andExpect(jsonPath("$[3].capacity").value(testAircraftResponses.get(3).getCapacity()))
                .andExpect(jsonPath("$[3].createdAt").exists())
                .andExpect(jsonPath("$[3].updatedAt").hasJsonPath())
                .andExpect(jsonPath("$[4].id").value(testAircraftResponses.get(4).getId()))
                .andExpect(jsonPath("$[4].registrationCode").value(testAircraftResponses.get(4).getRegistrationCode()))
                .andExpect(
                        jsonPath("$[4].registrationPrefix").value(testAircraftResponses.get(4).getRegistrationPrefix()))
                .andExpect(jsonPath("$[4].airlineId").value(testAircraftResponses.get(4).getAirlineId()))
                .andExpect(jsonPath("$[4].type").value(testAircraftResponses.get(4).getType()))
                .andExpect(jsonPath("$[4].capacity").value(testAircraftResponses.get(4).getCapacity()))
                .andExpect(jsonPath("$[4].createdAt").exists())
                .andExpect(jsonPath("$[4].updatedAt").hasJsonPath())
                .andExpect(jsonPath("$[5].id").value(testAircraftResponses.get(5).getId()))
                .andExpect(jsonPath("$[5].registrationCode").value(testAircraftResponses.get(5).getRegistrationCode()))
                .andExpect(
                        jsonPath("$[5].registrationPrefix").value(testAircraftResponses.get(5).getRegistrationPrefix()))
                .andExpect(jsonPath("$[5].airlineId").value(testAircraftResponses.get(5).getAirlineId()))
                .andExpect(jsonPath("$[5].type").value(testAircraftResponses.get(5).getType()))
                .andExpect(jsonPath("$[5].capacity").value(testAircraftResponses.get(5).getCapacity()))
                .andExpect(jsonPath("$[5].createdAt").exists())
                .andExpect(jsonPath("$[5].updatedAt").hasJsonPath())
                .andExpect(jsonPath("$[6].id").value(testAircraftResponses.get(6).getId()))
                .andExpect(jsonPath("$[6].registrationCode").value(testAircraftResponses.get(6).getRegistrationCode()))
                .andExpect(
                        jsonPath("$[6].registrationPrefix").value(testAircraftResponses.get(6).getRegistrationPrefix()))
                .andExpect(jsonPath("$[6].airlineId").value(testAircraftResponses.get(6).getAirlineId()))
                .andExpect(jsonPath("$[6].type").value(testAircraftResponses.get(6).getType()))
                .andExpect(jsonPath("$[6].capacity").value(testAircraftResponses.get(6).getCapacity()))
                .andExpect(jsonPath("$[6].createdAt").exists())
                .andExpect(jsonPath("$[6].updatedAt").hasJsonPath());

    }

    @Test
    @DisplayName("GET /api/aircraft/{id} - Should return aircraft by id")
    void shouldGetAircraftById() throws Exception {
        mockMvc.perform(get(API_AIRCRAFT + "/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(testAircraftResponses.get(0).getId()))
                .andExpect(jsonPath("$.registrationCode").value(testAircraftResponses.get(0).getRegistrationCode()))
                .andExpect(jsonPath("$.registrationPrefix").value(testAircraftResponses.get(0).getRegistrationPrefix()))
                .andExpect(jsonPath("$.airlineId").value(testAircraftResponses.get(0).getAirlineId()))
                .andExpect(jsonPath("$.type").value(testAircraftResponses.get(0).getType()))
                .andExpect(jsonPath("$.capacity").value(testAircraftResponses.get(0).getCapacity()))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").hasJsonPath());
    }

    @Test
    @DisplayName("GET /api/aircraft/{id} - Should return 404 when aircraft not found")
    void shouldReturn404WhenAircraftNotFound() throws Exception {
        mockMvc.perform(get(API_AIRCRAFT + "/100"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/aircraft - Should create new aircraft")
    void shouldCreateNewAircraft() throws Exception {
        AircraftDTO.AircraftRequest request = AircraftDTO.AircraftRequest.builder()
                .registrationCode("HB-AAA")
                .registrationPrefix("HB")
                .airlineId(1L)
                .type("Airbus A320neo")
                .capacity(180)
                .build();

        mockMvc.perform(post(API_AIRCRAFT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.registrationCode").value(request.getRegistrationCode()))
                .andExpect(jsonPath("$.registrationPrefix").value(request.getRegistrationPrefix()))
                .andExpect(jsonPath("$.airlineId").value(request.getAirlineId()))
                .andExpect(jsonPath("$.type").value(request.getType()))
                .andExpect(jsonPath("$.capacity").value(request.getCapacity()))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").hasJsonPath());
    }

    @Test
    @DisplayName("PUT /api/aircraft/{id} - Should update aircraft")
    void shouldUpdateAircraft() throws Exception {
        AircraftDTO.AircraftRequest request = AircraftDTO.AircraftRequest.builder()
                .registrationCode("HB-JCN")
                .registrationPrefix("HB")
                .airlineId(1L)
                .type("Airbus A320neo")
                .capacity(180)
                .build();

        mockMvc.perform(put(API_AIRCRAFT + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.registrationCode").value(request.getRegistrationCode()))
                .andExpect(jsonPath("$.registrationPrefix").value(request.getRegistrationPrefix()))
                .andExpect(jsonPath("$.airlineId").value(request.getAirlineId()))
                .andExpect(jsonPath("$.type").value(request.getType()))
                .andExpect(jsonPath("$.capacity").value(request.getCapacity()))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").hasJsonPath());
    }

    @Test
    @DisplayName("DELETE /api/aircraft/{id} - Should delete aircraft")
    void shouldDeleteAircraft() throws Exception {
        mockMvc.perform(delete(API_AIRCRAFT + "/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /api/aircraft/registrationCode/{code} - Should return aircraft by registration code")
    void shouldGetAircraftByRegistrationCode() throws Exception {
        mockMvc.perform(get(API_AIRCRAFT + "/registrationCode/HB-JCN"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(testAircraftResponses.get(0).getId()))
                .andExpect(jsonPath("$.registrationCode").value(testAircraftResponses.get(0).getRegistrationCode()))
                .andExpect(jsonPath("$.registrationPrefix").value(testAircraftResponses.get(0).getRegistrationPrefix()))
                .andExpect(jsonPath("$.airlineId").value(testAircraftResponses.get(0).getAirlineId()))
                .andExpect(jsonPath("$.type").value(testAircraftResponses.get(0).getType()))
                .andExpect(jsonPath("$.capacity").value(testAircraftResponses.get(0).getCapacity()))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").hasJsonPath());
    }

    @Test
    @DisplayName("GET /api/aircraft/registrationPrefix/{prefix} - Should return aircraft by registration prefix")
    void shouldGetAircraftByRegistrationPrefix() throws Exception {
        mockMvc.perform(get(API_AIRCRAFT + "/registrationPrefix/HB"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(
                        testAircraftResponses.stream().filter(a -> a.getRegistrationPrefix().equals("HB")).count()))
                .andExpect(jsonPath("$[0].id").value(testAircraftResponses.get(0).getId()))
                .andExpect(jsonPath("$[0].registrationCode").value(testAircraftResponses.get(0).getRegistrationCode()))
                .andExpect(
                        jsonPath("$[0].registrationPrefix").value(testAircraftResponses.get(0).getRegistrationPrefix()))
                .andExpect(jsonPath("$[0].airlineId").value(testAircraftResponses.get(0).getAirlineId()))
                .andExpect(jsonPath("$[0].type").value(testAircraftResponses.get(0).getType()))
                .andExpect(jsonPath("$[0].capacity").value(testAircraftResponses.get(0).getCapacity()))
                .andExpect(jsonPath("$[0].createdAt").exists())
                .andExpect(jsonPath("$[0].updatedAt").hasJsonPath())
                .andExpect(jsonPath("$[1].id").value(testAircraftResponses.get(1).getId()))
                .andExpect(jsonPath("$[1].registrationCode").value(testAircraftResponses.get(1).getRegistrationCode()))
                .andExpect(
                        jsonPath("$[1].registrationPrefix").value(testAircraftResponses.get(1).getRegistrationPrefix()))
                .andExpect(jsonPath("$[1].airlineId").value(testAircraftResponses.get(1).getAirlineId()))
                .andExpect(jsonPath("$[1].type").value(testAircraftResponses.get(1).getType()))
                .andExpect(jsonPath("$[1].capacity").value(testAircraftResponses.get(1).getCapacity()))
                .andExpect(jsonPath("$[1].createdAt").exists())
                .andExpect(jsonPath("$[1].updatedAt").hasJsonPath());
    }

    @Test
    @DisplayName("GET /api/aircraft/airline/{airlineId} - Should return aircraft by airline")
    void shouldGetAircraftByAirline() throws Exception {
        mockMvc.perform(get(API_AIRCRAFT + "/airline/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()")
                        .value(testAircraftResponses.stream().filter(a -> a.getAirlineId() == 1L).count()))
                .andExpect(jsonPath("$[0].id").value(testAircraftResponses.get(0).getId()))
                .andExpect(jsonPath("$[0].registrationCode").value(testAircraftResponses.get(0).getRegistrationCode()))
                .andExpect(
                        jsonPath("$[0].registrationPrefix").value(testAircraftResponses.get(0).getRegistrationPrefix()))
                .andExpect(jsonPath("$[0].airlineId").value(testAircraftResponses.get(0).getAirlineId()))
                .andExpect(jsonPath("$[0].type").value(testAircraftResponses.get(0).getType()))
                .andExpect(jsonPath("$[0].capacity").value(testAircraftResponses.get(0).getCapacity()))
                .andExpect(jsonPath("$[0].createdAt").exists())
                .andExpect(jsonPath("$[0].updatedAt").hasJsonPath())
                .andExpect(jsonPath("$[1].id").value(testAircraftResponses.get(1).getId()))
                .andExpect(jsonPath("$[1].registrationCode").value(testAircraftResponses.get(1).getRegistrationCode()))
                .andExpect(
                        jsonPath("$[1].registrationPrefix").value(testAircraftResponses.get(1).getRegistrationPrefix()))
                .andExpect(jsonPath("$[1].airlineId").value(testAircraftResponses.get(1).getAirlineId()))
                .andExpect(jsonPath("$[1].type").value(testAircraftResponses.get(1).getType()))
                .andExpect(jsonPath("$[1].capacity").value(testAircraftResponses.get(1).getCapacity()))
                .andExpect(jsonPath("$[1].createdAt").exists())
                .andExpect(jsonPath("$[1].updatedAt").hasJsonPath());
    }

    @Test
    @DisplayName("GET /api/aircraft/type/{type} - Should return aircraft by type")
    void shouldGetAircraftByType() throws Exception {
        mockMvc.perform(get(API_AIRCRAFT + "/type/Airbus A320neo"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(
                        testAircraftResponses.stream().filter(a -> a.getType().equals("Airbus A320neo")).count()))
                .andExpect(jsonPath("$[0].id").value(testAircraftResponses.get(0).getId()))
                .andExpect(jsonPath("$[0].registrationCode").value(testAircraftResponses.get(0).getRegistrationCode()))
                .andExpect(
                        jsonPath("$[0].registrationPrefix").value(testAircraftResponses.get(0).getRegistrationPrefix()))
                .andExpect(jsonPath("$[0].airlineId").value(testAircraftResponses.get(0).getAirlineId()))
                .andExpect(jsonPath("$[0].type").value(testAircraftResponses.get(0).getType()))
                .andExpect(jsonPath("$[0].capacity").value(testAircraftResponses.get(0).getCapacity()))
                .andExpect(jsonPath("$[0].createdAt").exists())
                .andExpect(jsonPath("$[0].updatedAt").hasJsonPath())
                .andExpect(jsonPath("$[1].id").value(testAircraftResponses.get(1).getId()))
                .andExpect(jsonPath("$[1].registrationCode").value(testAircraftResponses.get(1).getRegistrationCode()))
                .andExpect(
                        jsonPath("$[1].registrationPrefix").value(testAircraftResponses.get(1).getRegistrationPrefix()))
                .andExpect(jsonPath("$[1].airlineId").value(testAircraftResponses.get(1).getAirlineId()))
                .andExpect(jsonPath("$[1].type").value(testAircraftResponses.get(1).getType()))
                .andExpect(jsonPath("$[1].capacity").value(testAircraftResponses.get(1).getCapacity()))
                .andExpect(jsonPath("$[1].createdAt").exists())
                .andExpect(jsonPath("$[1].updatedAt").hasJsonPath())
                .andExpect(jsonPath("$[2].id").value(testAircraftResponses.get(3).getId()))
                .andExpect(jsonPath("$[2].registrationCode").value(testAircraftResponses.get(3).getRegistrationCode()))
                .andExpect(
                        jsonPath("$[2].registrationPrefix").value(testAircraftResponses.get(3).getRegistrationPrefix()))
                .andExpect(jsonPath("$[2].airlineId").value(testAircraftResponses.get(3).getAirlineId()))
                .andExpect(jsonPath("$[2].type").value(testAircraftResponses.get(3).getType()))
                .andExpect(jsonPath("$[2].capacity").value(testAircraftResponses.get(3).getCapacity()))
                .andExpect(jsonPath("$[2].createdAt").exists())
                .andExpect(jsonPath("$[2].updatedAt").hasJsonPath())
                .andExpect(jsonPath("$[3].id").value(testAircraftResponses.get(5).getId()))
                .andExpect(jsonPath("$[3].registrationCode").value(testAircraftResponses.get(5).getRegistrationCode()))
                .andExpect(
                        jsonPath("$[3].registrationPrefix").value(testAircraftResponses.get(5).getRegistrationPrefix()))
                .andExpect(jsonPath("$[3].airlineId").value(testAircraftResponses.get(5).getAirlineId()))
                .andExpect(jsonPath("$[3].type").value(testAircraftResponses.get(5).getType()))
                .andExpect(jsonPath("$[3].capacity").value(testAircraftResponses.get(5).getCapacity()))
                .andExpect(jsonPath("$[3].createdAt").exists())
                .andExpect(jsonPath("$[3].updatedAt").hasJsonPath())
                .andExpect(jsonPath("$[4].id").value(testAircraftResponses.get(6).getId()))
                .andExpect(jsonPath("$[4].registrationCode").value(testAircraftResponses.get(6).getRegistrationCode()))
                .andExpect(
                        jsonPath("$[4].registrationPrefix").value(testAircraftResponses.get(6).getRegistrationPrefix()))
                .andExpect(jsonPath("$[4].airlineId").value(testAircraftResponses.get(6).getAirlineId()))
                .andExpect(jsonPath("$[4].type").value(testAircraftResponses.get(6).getType()))
                .andExpect(jsonPath("$[4].capacity").value(testAircraftResponses.get(6).getCapacity()))
                .andExpect(jsonPath("$[4].createdAt").exists())
                .andExpect(jsonPath("$[4].updatedAt").hasJsonPath());
    }

    @Test
    @DisplayName("GET /api/aircraft/capacity/{capacity} - Should return aircraft by capacity")
    void shouldGetAircraftByCapacity() throws Exception {
        mockMvc.perform(get(API_AIRCRAFT + "/capacity/180"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()")
                        .value(testAircraftResponses.stream().filter(a -> a.getCapacity() == 180).count()))
                .andExpect(jsonPath("$[0].id").value(testAircraftResponses.get(0).getId()))
                .andExpect(jsonPath("$[0].registrationCode").value(testAircraftResponses.get(0).getRegistrationCode()))
                .andExpect(
                        jsonPath("$[0].registrationPrefix").value(testAircraftResponses.get(0).getRegistrationPrefix()))
                .andExpect(jsonPath("$[0].airlineId").value(testAircraftResponses.get(0).getAirlineId()))
                .andExpect(jsonPath("$[0].type").value(testAircraftResponses.get(0).getType()))
                .andExpect(jsonPath("$[0].capacity").value(testAircraftResponses.get(0).getCapacity()))
                .andExpect(jsonPath("$[0].createdAt").exists())
                .andExpect(jsonPath("$[0].updatedAt").hasJsonPath())
                .andExpect(jsonPath("$[1].id").value(testAircraftResponses.get(1).getId()))
                .andExpect(jsonPath("$[1].registrationCode").value(testAircraftResponses.get(1).getRegistrationCode()))
                .andExpect(
                        jsonPath("$[1].registrationPrefix").value(testAircraftResponses.get(1).getRegistrationPrefix()))
                .andExpect(jsonPath("$[1].airlineId").value(testAircraftResponses.get(1).getAirlineId()))
                .andExpect(jsonPath("$[1].type").value(testAircraftResponses.get(1).getType()))
                .andExpect(jsonPath("$[1].capacity").value(testAircraftResponses.get(1).getCapacity()))
                .andExpect(jsonPath("$[1].createdAt").exists())
                .andExpect(jsonPath("$[1].updatedAt").hasJsonPath())
                .andExpect(jsonPath("$[2].id").value(testAircraftResponses.get(3).getId()))
                .andExpect(jsonPath("$[2].registrationCode").value(testAircraftResponses.get(3).getRegistrationCode()))
                .andExpect(
                        jsonPath("$[2].registrationPrefix").value(testAircraftResponses.get(3).getRegistrationPrefix()))
                .andExpect(jsonPath("$[2].airlineId").value(testAircraftResponses.get(3).getAirlineId()))
                .andExpect(jsonPath("$[2].type").value(testAircraftResponses.get(3).getType()))
                .andExpect(jsonPath("$[2].capacity").value(testAircraftResponses.get(3).getCapacity()))
                .andExpect(jsonPath("$[2].createdAt").exists())
                .andExpect(jsonPath("$[2].updatedAt").hasJsonPath())
                .andExpect(jsonPath("$[3].id").value(testAircraftResponses.get(6).getId()))
                .andExpect(jsonPath("$[3].registrationCode").value(testAircraftResponses.get(6).getRegistrationCode()))
                .andExpect(
                        jsonPath("$[3].registrationPrefix").value(testAircraftResponses.get(6).getRegistrationPrefix()))
                .andExpect(jsonPath("$[3].airlineId").value(testAircraftResponses.get(6).getAirlineId()))
                .andExpect(jsonPath("$[3].type").value(testAircraftResponses.get(6).getType()))
                .andExpect(jsonPath("$[3].capacity").value(testAircraftResponses.get(6).getCapacity()))
                .andExpect(jsonPath("$[3].createdAt").exists())
                .andExpect(jsonPath("$[3].updatedAt").hasJsonPath());
    }

    @Test
    @DisplayName("GET /api/aircraft/capacity/{min}/{max} - Should return aircraft by capacity range")
    void shouldGetAircraftByCapacityRange() throws Exception {
        mockMvc.perform(get(API_AIRCRAFT + "/capacity/160/170"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(testAircraftResponses.stream()
                        .filter(a -> a.getCapacity() >= 160 && a.getCapacity() <= 170).count()))
                .andExpect(jsonPath("$[0].id").value(testAircraftResponses.get(2).getId()))
                .andExpect(jsonPath("$[0].registrationCode").value(testAircraftResponses.get(2).getRegistrationCode()))
                .andExpect(
                        jsonPath("$[0].registrationPrefix").value(testAircraftResponses.get(2).getRegistrationPrefix()))
                .andExpect(jsonPath("$[0].airlineId").value(testAircraftResponses.get(2).getAirlineId()))
                .andExpect(jsonPath("$[0].type").value(testAircraftResponses.get(2).getType()))
                .andExpect(jsonPath("$[0].capacity").value(testAircraftResponses.get(2).getCapacity()))
                .andExpect(jsonPath("$[0].createdAt").exists())
                .andExpect(jsonPath("$[0].updatedAt").hasJsonPath());
    }

    @Test
    @DisplayName("GET /api/aircraft/airline/{airlineId}/type/{type} - Should return aircraft by airline and type")
    void shouldGetAircraftByAirlineAndType() throws Exception {
        mockMvc.perform(get(API_AIRCRAFT + "/airline/1/type/Airbus A320neo"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(testAircraftResponses.stream()
                        .filter(a -> a.getAirlineId() == 1L && a.getType().equals("Airbus A320neo")).count()))
                .andExpect(jsonPath("$[0].id").value(testAircraftResponses.get(0).getId()))
                .andExpect(jsonPath("$[0].registrationCode").value(testAircraftResponses.get(0).getRegistrationCode()))
                .andExpect(
                        jsonPath("$[0].registrationPrefix").value(testAircraftResponses.get(0).getRegistrationPrefix()))
                .andExpect(jsonPath("$[0].airlineId").value(testAircraftResponses.get(0).getAirlineId()))
                .andExpect(jsonPath("$[0].type").value(testAircraftResponses.get(0).getType()))
                .andExpect(jsonPath("$[0].capacity").value(testAircraftResponses.get(0).getCapacity()))
                .andExpect(jsonPath("$[0].createdAt").exists())
                .andExpect(jsonPath("$[0].updatedAt").hasJsonPath())
                .andExpect(jsonPath("$[1].id").value(testAircraftResponses.get(1).getId()))
                .andExpect(jsonPath("$[1].registrationCode").value(testAircraftResponses.get(1).getRegistrationCode()))
                .andExpect(
                        jsonPath("$[1].registrationPrefix").value(testAircraftResponses.get(1).getRegistrationPrefix()))
                .andExpect(jsonPath("$[1].airlineId").value(testAircraftResponses.get(1).getAirlineId()))
                .andExpect(jsonPath("$[1].type").value(testAircraftResponses.get(1).getType()))
                .andExpect(jsonPath("$[1].capacity").value(testAircraftResponses.get(1).getCapacity()))
                .andExpect(jsonPath("$[1].createdAt").exists())
                .andExpect(jsonPath("$[1].updatedAt").hasJsonPath());
    }
}
