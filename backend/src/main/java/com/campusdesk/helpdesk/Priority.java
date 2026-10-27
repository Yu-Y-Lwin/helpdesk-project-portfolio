package com.campusdesk.helpdesk;

import com.fasterxml.jackson.annotation.JsonValue;

/** Ticket priority. Business rule BR-01: each priority has a target resolution time (SLA). */
public enum Priority {
    LOW("low", 72),
    MEDIUM("medium", 24),
    HIGH("high", 4);

    private final String label;
    private final int slaHours;

    Priority(String label, int slaHours) {
        this.label = label;
        this.slaHours = slaHours;
    }

    @JsonValue
    public String label() {
        return label;
    }

    public int slaHours() {
        return slaHours;
    }

    public static Priority parse(String value) {
        for (Priority priority : values()) {
            if (priority.label.equals(value)) {
                return priority;
            }
        }
        throw new InvalidInputException("Invalid priority: " + value);
    }
}
