package com.airport.vms.repository;

import com.airport.vms.domain.Visit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    Optional<Visit> findByBadgeNumber(String badgeNumber);

    Page<Visit> findByStatus(Visit.VisitStatus status, Pageable pageable);

    @Query("SELECT v FROM Visit v WHERE v.visitor.id = :visitorId AND v.active = true")
    List<Visit> findActiveByVisitorId(Long visitorId);

    @Query("SELECT v FROM Visit v WHERE v.active = true")
    Page<Visit> findActiveVisits(Pageable pageable);
}
