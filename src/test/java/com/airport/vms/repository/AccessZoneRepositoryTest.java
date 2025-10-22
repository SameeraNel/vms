package com.airport.vms.repository;

import com.airport.vms.domain.AccessZone;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AccessZoneRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccessZoneRepository accessZoneRepository;

    @Test
    public void whenFindByCode_thenReturnAccessZone() {
        // given
        AccessZone accessZone = new AccessZone();
        accessZone.setName("Terminal 1");
        accessZone.setCode("T1_SECURE");
        accessZone.setCreatedAt(LocalDateTime.now());
        entityManager.persist(accessZone);
        entityManager.flush();

        // when
        Optional<AccessZone> found = accessZoneRepository.findByCode(accessZone.getCode());

        // then
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getCode()).isEqualTo(accessZone.getCode());
    }
}
