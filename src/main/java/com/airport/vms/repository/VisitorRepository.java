package com.airport.vms.repository;

import com.airport.vms.domain.Visitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {

    Optional<Visitor> findByEmail(String email);

    Optional<Visitor> findByPhone(String phone);

    @Query("SELECT v FROM Visitor v WHERE " +
            "LOWER(v.firstName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(v.lastName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(v.organization) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Visitor> searchByNameOrOrganization(String query, Pageable pageable);
}
