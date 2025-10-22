package com.airport.vms.repository;

import com.airport.vms.domain.Visitor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class VisitorRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private VisitorRepository visitorRepository;

    @Test
    public void whenFindByEmail_thenReturnVisitor() {
        // given
        Visitor visitor = new Visitor();
        visitor.setFirstName("John");
        visitor.setLastName("Doe");
        visitor.setEmail("john.doe@example.com");
        visitor.setCreatedAt(LocalDateTime.now());
        entityManager.persist(visitor);
        entityManager.flush();

        // when
        Optional<Visitor> found = visitorRepository.findByEmail(visitor.getEmail());

        // then
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getEmail()).isEqualTo(visitor.getEmail());
    }

    @Test
    public void whenFindByPhone_thenReturnVisitor() {
        // given
        Visitor visitor = new Visitor();
        visitor.setFirstName("Jane");
        visitor.setLastName("Doe");
        visitor.setPhone("1234567890");
        visitor.setCreatedAt(LocalDateTime.now());
        entityManager.persist(visitor);
        entityManager.flush();

        // when
        Optional<Visitor> found = visitorRepository.findByPhone(visitor.getPhone());

        // then
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getPhone()).isEqualTo(visitor.getPhone());
    }

    @Test
    public void whenSearchByNameOrOrganization_thenReturnPageOfVisitors() {
        // given
        Visitor visitor1 = new Visitor();
        visitor1.setFirstName("Alice");
        visitor1.setLastName("Smith");
        visitor1.setOrganization("Acme Corp");
        visitor1.setCreatedAt(LocalDateTime.now());
        entityManager.persist(visitor1);

        Visitor visitor2 = new Visitor();
        visitor2.setFirstName("Bob");
        visitor2.setLastName("Johnson");
        visitor2.setOrganization("Globex");
        visitor2.setCreatedAt(LocalDateTime.now());
        entityManager.persist(visitor2);

        entityManager.flush();

        // when
        Page<Visitor> found = visitorRepository.searchByNameOrOrganization("Acme", PageRequest.of(0, 10));

        // then
        assertThat(found.getTotalElements()).isEqualTo(1);
        assertThat(found.getContent().get(0).getOrganization()).isEqualTo("Acme Corp");
    }
}
