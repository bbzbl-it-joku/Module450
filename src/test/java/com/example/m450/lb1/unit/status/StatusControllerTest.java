package com.example.m450.lb1.unit.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.m450.lb1.status.StatusController;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatusController.class)
public class StatusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Clock clock;

    private final LocalDateTime fixedDateTime = LocalDateTime.of(2024, 11, 5, 10, 0, 0);

    @BeforeEach
    public void setup() {
        // Freeze time to a fixed date and time
        Clock fixedClock = Clock.fixed(fixedDateTime.toInstant(ZoneOffset.UTC), ZoneId.of("UTC"));
        org.mockito.Mockito.when(clock.instant()).thenReturn(fixedClock.instant());
        org.mockito.Mockito.when(clock.getZone()).thenReturn(fixedClock.getZone());
    }

    @Test
    public void testGetCurrentDateTime() throws Exception {
        // Perform GET request to /status endpoint
        MvcResult result = mockMvc.perform(get("/status"))
                .andExpect(status().isOk())
                .andReturn();

        // Extract response content as a string
        String responseContent = result.getResponse().getContentAsString().replace("\"", "");

        // Assert that the response matches the fixed date and time
        assertEquals(fixedDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), responseContent);
    }
}
