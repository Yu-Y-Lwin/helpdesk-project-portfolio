package com.campusdesk.helpdesk;

import java.time.Instant;

public class Ticket {

    private final int id;
    private final String title;
    private final String description;
    private final Priority priority;
    private final String requesterEmail;
    private Status status;
    private String assignee;
    private final int slaHours;
    private final Instant createdAt;

    public Ticket(int id, String title, String description, Priority priority, String requesterEmail) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.requesterEmail = requesterEmail;
        this.status = Status.OPEN;
        this.assignee = null;
        this.slaHours = priority.slaHours();
        this.createdAt = Instant.now();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    public String getRequesterEmail() {
        return requesterEmail;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public int getSlaHours() {
        return slaHours;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
