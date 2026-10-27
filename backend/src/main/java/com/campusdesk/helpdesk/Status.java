package com.campusdesk.helpdesk;

import com.fasterxml.jackson.annotation.JsonValue;

/** Ticket status. Business rule BR-02 decides which changes are allowed. */
public enum Status {
    OPEN("open"),
    IN_PROGRESS("in_progress"),
    RESOLVED("resolved"),
    CLOSED("closed");

    private final String label;

    Status(String label) {
        this.label = label;
    }

    @JsonValue
    public String label() {
        return label;
    }

    public boolean canMoveTo(Status next) {
        return switch (this) {
            case OPEN -> next == IN_PROGRESS;
            case IN_PROGRESS -> next == RESOLVED;
            case RESOLVED -> next == CLOSED || next == OPEN; // a resolved ticket can be reopened
            case CLOSED -> false;
        };
    }

    public static Status parse(String value) {
        for (Status status : values()) {
            if (status.label.equals(value)) {
                return status;
            }
        }
        throw new InvalidInputException("Invalid status: " + value);
    }
}
