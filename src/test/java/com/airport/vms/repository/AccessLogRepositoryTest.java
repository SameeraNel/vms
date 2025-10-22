package com.airport.vms.repository;

import com.airport.vms.domain.AccessLog;
import com.airport.vms.domain.Visit;
import com.airport.vms.domain.Visitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AccessLogRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccessLogRepository accessLogRepository;

    private Visit visit;

    @BeforeEach
    void setUp() {
        Visitor visitor = new Visitor();
        visitor.setFirstName("Test");
        visitor.setLastName("Visitor");
        visitor.setCreatedAt(LocalDateTime.now());
        entityManager.persist(visitor);

        visit = new Visit();
        visit.setVisitor(visitor);
        visit.setStatus(Visit.VisitStatus.PRE_REGISTERED);
        visit.setCreatedAt(LocalDateTime.now());
        entityManager.persist(visit);
        entityManager.flush();
    }

    @Test
    public void whenFindByVisitId_thenReturnPageOfAccessLogs() {
        // given
        AccessLog log1 = new AccessLog();
        log1.setVisit(visit);
        log1.setEventType(AccessLog.EventType.PRE_REGISTER);
        log1.setTimestamp(LocalDateTime.now());
        log1.setCreatedAt(LocalDateTime.now());
        entityManager.persist(log1);
        entityManager.flush();

        // when
        Page<AccessLog> found = accessLogRepository.findByVisitId(visit.getId(), PageRequest.of(0, 10));

        // then
        assertThat(found.getTotalElements()).isEqualTo(1);
        assertThat(found.getContent().get(0).getVisit().getId()).isEqualTo(visit.getId());
    }

    @Test
    public void whenFindByEventTypeAndTimestampBetween_thenReturnPageOfAccessLogs() {
        // given
        AccessLog log1 = new AccessLog();
        log1.setVisit(visit);
        log1.setEventType(AccessLog.EventType.CHECK_IN);
        log1.setTimestamp(LocalDateTime.now().minusHours(1));
        log1.setCreatedAt(LocalDateTime.now());
        entityManager.persist(log1);
        entityManager.flush();

        // when
        Page<AccessLog> found = accessLogRepository.findByEventTypeAndTimestampBetween(
                AccessLog.EventType.CHECK_IN,
                LocalDateTime.now().minusHours(2),
                LocalDateTime.now(),
                PageRequest.of(0, 10)
        );

        // then
        assertThat(found.getTotalElements()).isEqualTo(1);
        assertThat(found.getContent().get(0).getEventType()).isEqualTo(AccessLog.EventType.CHECK_IN);
    }
}
