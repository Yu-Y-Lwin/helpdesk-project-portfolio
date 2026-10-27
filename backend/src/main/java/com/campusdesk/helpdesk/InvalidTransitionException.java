package com.campusdesk.helpdesk;

public class InvalidTransitionException extends RuntimeException {

    public InvalidTransitionException(Status from, Status to) {
        super("Cannot change status from '" + from.label() + "' to '" + to.label() + "'");
    }
}
