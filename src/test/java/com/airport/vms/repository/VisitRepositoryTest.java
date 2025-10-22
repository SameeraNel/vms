package com.airport.vms.repository;

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
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class VisitRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private VisitRepository visitRepository;

    private Visitor visitor;

    @BeforeEach
    void setUp() {
        visitor = new Visitor();
        visitor.setFirstName("Test");
        visitor.setLastName("Visitor");
        visitor.setCreatedAt(LocalDateTime.now());
        entityManager.persist(visitor);
        entityManager.flush();
    }

    @Test
    public void whenFindByBadgeNumber_thenReturnVisit() {
        // given
        Visit visit = new Visit();
        visit.setVisitor(visitor);
        visit.setStatus(Visit.VisitStatus.PRE_REGISTERED);
        visit.setBadgeNumber("BADGE123");
        visit.setCreatedAt(LocalDateTime.now());
        entityManager.persist(visit);
        entityManager.flush();

        // when
        Optional<Visit> found = visitRepository.findByBadgeNumber(visit.getBadgeNumber());

        // then
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getBadgeNumber()).isEqualTo(visit.getBadgeNumber());
    }

    @Test
    public void whenFindByStatus_thenReturnPageOfVisits() {
        // given
        Visit visit1 = new Visit();
        visit1.setVisitor(visitor);
        visit1.setStatus(Visit.VisitStatus.PRE_REGISTERED);
        visit1.setCreatedAt(LocalDateTime.now());
        entityManager.persist(visit1);

        Visit visit2 = new Visit();
        visit2.setVisitor(visitor);
        visit2.setStatus(Visit.VisitStatus.CHECKED_IN);
        visit2.setCreatedAt(LocalDateTime.now());
        entityManager.persist(visit2);

        entityManager.flush();

        // when
        Page<Visit> found = visitRepository.findByStatus(Visit.VisitStatus.PRE_REGISTERED, PageRequest.of(0, 10));

        // then
        assertThat(found.getTotalElements()).isEqualTo(1);
        assertThat(found.getContent().get(0).getStatus()).isEqualTo(Visit.VisitStatus.PRE_REGISTERED);
    }

    @Test
    public void whenFindActiveByVisitorId_thenReturnListOfVisits() {
        // given
        Visit visit1 = new Visit();
        visit1.setVisitor(visitor);
        visit1.setActive(true);
        visit1.setStatus(Visit.VisitStatus.PRE_REGISTERED);
        visit1.setCreatedAt(LocalDateTime.now());
        entityManager.persist(visit1);

        Visit visit2 = new Visit();
        visit2.setVisitor(visitor);
        visit2.setActive(false);
        visit2.setStatus(Visit.VisitStatus.CHECKED_OUT);
        visit2.setCreatedAt(LocalDateTime.now());
        entityManager.persist(visit2);

        entityManager.flush();

        // when
        List<Visit> found = visitRepository.findActiveByVisitorId(visitor.getId());

        // then
        assertThat(found).hasSize(1);
        assertThat(found.get(0).isActive()).isTrue();
    }

    @Test
    public void whenFindActiveVisits_thenReturnPageOfVisits() {
        // given
        Visit visit1 = new Visit();
        visit1.setVisitor(visitor);
        visit1.setActive(true);
        visit1.setStatus(Visit.VisitStatus.PRE_REGISTERED);
        visit1.setCreatedAt(LocalDateTime.now());
        entityManager.persist(visit1);

        Visit visit2 = new Visit();
        visit2.setVisitor(visitor);
        visit2.setActive(false);
        visit2.setStatus(Visit.VisitStatus.CHECKED_OUT);
        visit2.setCreatedAt(LocalDateTime.now());
        entityManager.persist(visit2);

        entityManager.flush();

        // when
        Page<Visit> found = visitRepository.findActiveVisits(PageRequest.of(0, 10));

        // then
        assertThat(found.getTotalElements()).isEqualTo(1);
        assertThat(found.getContent().get(0).isActive()).isTrue();
    }
}
