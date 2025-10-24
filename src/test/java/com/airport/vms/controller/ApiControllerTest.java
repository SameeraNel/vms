package com.airport.vms.controller;

import com.airport.vms.domain.Employee;
import com.airport.vms.domain.Visit;
import com.airport.vms.domain.Visitor;
import com.airport.vms.dto.*;
import com.airport.vms.repository.EmployeeRepository;
import com.airport.vms.repository.VisitRepository;
import com.airport.vms.repository.VisitorRepository;
import com.airport.vms.security.JwtUtil;
import com.airport.vms.service.MyUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private VisitRepository visitRepository;

    private String adminToken;
    private String kioskToken;
    private String guardToken;
    private Visitor visitor;
    private Employee employee;

    @BeforeEach
    void setUp() {
        UserDetails adminDetails = myUserDetailsService.loadUserByUsername("admin");
        adminToken = jwtUtil.generateToken(adminDetails);

        UserDetails kioskDetails = myUserDetailsService.loadUserByUsername("kiosk");
        kioskToken = jwtUtil.generateToken(kioskDetails);

        UserDetails guardDetails = myUserDetailsService.loadUserByUsername("guard");
        guardToken = jwtUtil.generateToken(guardDetails);

        visitor = new Visitor();
        visitor.setFirstName("Test");
        visitor.setLastName("Visitor");
        visitor.setCreatedAt(LocalDateTime.now());
        visitorRepository.save(visitor);

        employee = new Employee();
        employee.setName("Test Host");
        employee.setCreatedAt(LocalDateTime.now());
        employeeRepository.save(employee);

        Visit visit = new Visit();
        visit.setVisitor(visitor);
        visit.setHost(employee);
        visit.setStatus(Visit.VisitStatus.PRE_REGISTERED);
        visit.setCreatedAt(LocalDateTime.now());
        visit.setPermittedZones(Collections.emptySet());
        visitRepository.save(visit);
    }

    @Test
    void whenAccessProtectedEndpoint_withoutToken_thenReturns403() throws Exception {
        mockMvc.perform(get("/api/v1/visitors"))
                .andExpect(status().isForbidden());
    }

    @Test
    void whenCreateVisitor_withAdminToken_thenReturns201() throws Exception {
        VisitorDto.VisitorRequest request = new VisitorDto.VisitorRequest("John", "Doe", null, "john.doe@example.com", null, null, null, null, null);
        mockMvc.perform(post("/api/v1/visitors")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void whenCreateVisitor_withKioskToken_thenReturns403() throws Exception {
        VisitorDto.VisitorRequest request = new VisitorDto.VisitorRequest("John", "Doe", null, "john.doe@example.com", null, null, null, null, null);
        mockMvc.perform(post("/api/v1/visitors")
                        .header("Authorization", "Bearer " + kioskToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    void whenCheckIn_withKioskToken_thenReturns200() throws Exception {
        VisitDto.CheckInRequest request = new VisitDto.CheckInRequest(1L, null, "kiosk123", null);
        mockMvc.perform(post("/api/v1/visits/1/checkin")
                        .header("Authorization", "Bearer " + kioskToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void whenCheckIn_withGuardToken_thenReturns200() throws Exception {
        VisitDto.CheckInRequest request = new VisitDto.CheckInRequest(1L, null, null, "guard123");
        mockMvc.perform(post("/api/v1/visits/1/checkin")
                        .header("Authorization", "Bearer " + guardToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void whenPushVisitsBatch_withKioskToken_thenReturns200() throws Exception {
        VisitorDto.VisitorRequest visitorRequest = new VisitorDto.VisitorRequest("Jane", "Doe", null, null, null, null, null, null, null);
        SyncDto.VisitSyncRequest syncRequest = new SyncDto.VisitSyncRequest("local1", "key1", new VisitDto.VisitCreateRequest(null, visitorRequest, employee.getId(), null, null, null, null, null));
        mockMvc.perform(post("/api/v1/sync/visits-batch?kioskId=kiosk123")
                        .header("Authorization", "Bearer " + kioskToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Collections.singletonList(syncRequest))))
                .andExpect(status().isOk());
    }

    @Test
    void whenPushVisitsBatch_withSameIdempotencyKey_thenReturnsDuplicate() throws Exception {
        VisitorDto.VisitorRequest visitorRequest = new VisitorDto.VisitorRequest("Jane", "Doe", null, null, null, null, null, null, null);
        SyncDto.VisitSyncRequest syncRequest = new SyncDto.VisitSyncRequest("local1", "key2", new VisitDto.VisitCreateRequest(null, visitorRequest, employee.getId(), null, null, null, null, null));
        mockMvc.perform(post("/api/v1/sync/visits-batch?kioskId=kiosk123")
                        .header("Authorization", "Bearer " + kioskToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Collections.singletonList(syncRequest))))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/sync/visits-batch?kioskId=kiosk123")
                        .header("Authorization", "Bearer " + kioskToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Collections.singletonList(syncRequest))))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String content = result.getResponse().getContentAsString();
                    assert content.contains("DUPLICATE");
                });
    }
}
