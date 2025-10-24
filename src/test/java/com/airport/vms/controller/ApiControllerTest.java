package com.airport.vms.controller;

import com.airport.vms.dto.*;
import com.airport.vms.security.JwtUtil;
import com.airport.vms.service.MyUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    private String token;

    @BeforeEach
    void setUp() {
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername("admin");
        token = jwtUtil.generateToken(userDetails);
    }

    @Test
    void whenAccessProtectedEndpoint_withoutToken_thenReturns403() throws Exception {
        mockMvc.perform(get("/api/v1/visitors"))
                .andExpect(status().isForbidden());
    }

    @Test
    void whenCreateVisitor_withValidToken_thenReturns201() throws Exception {
        VisitorDto.VisitorRequest request = new VisitorDto.VisitorRequest("John", "Doe", null, "john.doe@example.com", null, null, null, null, null);
        mockMvc.perform(post("/api/v1/visitors")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}
