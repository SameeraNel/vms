package com.airport.vms.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "visits")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "visitorId")
    private Visitor visitor;

    @ManyToOne
    @JoinColumn(name = "hostId")
    private Employee host;

    private LocalDateTime plannedArrival;

    private LocalDateTime plannedDeparture;

    private LocalDateTime checkInTime;

    private LocalDateTime checkOutTime;

    @Column(unique = true)
    private String badgeNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private VisitStatus status;

    private boolean active = true;

    private String purpose;

    @ManyToMany
    @JoinTable(
            name = "visit_access",
            joinColumns = @JoinColumn(name = "visit_id"),
            inverseJoinColumns = @JoinColumn(name = "access_zone_id")
    )
    private Set<AccessZone> permittedZones;

    @NotNull
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String source;

    public enum VisitStatus {
        PRE_REGISTERED, CHECKED_IN, CHECKED_OUT, CANCELLED
    }

    public Visit() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public Employee getHost() {
        return host;
    }

    public void setHost(Employee host) {
        this.host = host;
    }

    public LocalDateTime getPlannedArrival() {
        return plannedArrival;
    }

    public void setPlannedArrival(LocalDateTime plannedArrival) {
        this.plannedArrival = plannedArrival;
    }

    public LocalDateTime getPlannedDeparture() {
        return plannedDeparture;
    }

    public void setPlannedDeparture(LocalDateTime plannedDeparture) {
        this.plannedDeparture = plannedDeparture;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(LocalDateTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getBadgeNumber() {
        return badgeNumber;
    }

    public void setBadgeNumber(String badgeNumber) {
        this.badgeNumber = badgeNumber;
    }

    public VisitStatus getStatus() {
        return status;
    }

    public void setStatus(VisitStatus status) {
        this.status = status;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Set<AccessZone> getPermittedZones() {
        return permittedZones;
    }

    public void setPermittedZones(Set<AccessZone> permittedZones) {
        this.permittedZones = permittedZones;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
