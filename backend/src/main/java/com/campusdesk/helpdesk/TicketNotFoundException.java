package com.campusdesk.helpdesk;

public class TicketNotFoundException extends RuntimeException {

    public TicketNotFoundException(int id) {
        super("Ticket " + id + " not found");
    }
}
