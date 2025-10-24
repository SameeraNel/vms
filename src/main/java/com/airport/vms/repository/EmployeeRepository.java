package com.airport.vms.repository;

import com.airport.vms.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByBadgeId(String badgeId);

    Page<Employee> findByDepartment(String department, Pageable pageable);

    java.util.List<Employee> findAllByUpdatedAtAfter(java.time.LocalDateTime timestamp);
}
