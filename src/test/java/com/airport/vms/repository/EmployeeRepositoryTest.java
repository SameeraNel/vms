package com.airport.vms.repository;

import com.airport.vms.domain.Employee;
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
public class EmployeeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void whenFindByBadgeId_thenReturnEmployee() {
        // given
        Employee employee = new Employee();
        employee.setName("Test Employee");
        employee.setBadgeId("12345");
        employee.setCreatedAt(LocalDateTime.now());
        entityManager.persist(employee);
        entityManager.flush();

        // when
        Optional<Employee> found = employeeRepository.findByBadgeId(employee.getBadgeId());

        // then
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getBadgeId()).isEqualTo(employee.getBadgeId());
    }

    @Test
    public void whenFindByDepartment_thenReturnPageOfEmployees() {
        // given
        Employee employee1 = new Employee();
        employee1.setName("Employee 1");
        employee1.setDepartment("IT");
        employee1.setCreatedAt(LocalDateTime.now());
        entityManager.persist(employee1);

        Employee employee2 = new Employee();
        employee2.setName("Employee 2");
        employee2.setDepartment("HR");
        employee2.setCreatedAt(LocalDateTime.now());
        entityManager.persist(employee2);

        entityManager.flush();

        // when
        Page<Employee> found = employeeRepository.findByDepartment("IT", PageRequest.of(0, 10));

        // then
        assertThat(found.getTotalElements()).isEqualTo(1);
        assertThat(found.getContent().get(0).getDepartment()).isEqualTo("IT");
    }
}
