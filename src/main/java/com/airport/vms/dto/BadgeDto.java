package com.airport.vms.dto;

public class BadgeDto {
    private final String badgeNumber;
    private final String qrPayload;

    public BadgeDto(String badgeNumber, String qrPayload) {
        this.badgeNumber = badgeNumber;
        this.qrPayload = qrPayload;
    }

    public String getBadgeNumber() {
        return badgeNumber;
    }

    public String getQrPayload() {
        return qrPayload;
    }
}
