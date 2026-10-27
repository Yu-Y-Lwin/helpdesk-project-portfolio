package com.campusdesk.helpdesk;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;

/** Business logic. Tickets are kept in memory only: this is a practice system. */
@Service
public class TicketService {

    private final Map<Integer, Ticket> tickets = new ConcurrentHashMap<>();
    private final AtomicInteger ids = new AtomicInteger(0);

    public Ticket create(CreateTicketRequest request) {
        Priority priority = request.priority() == null ? Priority.MEDIUM : request.priority();
        Ticket ticket = new Ticket(
                ids.incrementAndGet(),
                request.title(),
                request.description(),
                priority,
                request.requesterEmail());
        tickets.put(ticket.getId(), ticket);
        return ticket;
    }

    public List<Ticket> list(Status status, Priority priority) {
        return tickets.values().stream()
                .filter(ticket -> status == null || ticket.getStatus() == status)
                .filter(ticket -> priority == null || ticket.getPriority() == priority)
                .sorted(Comparator.comparingInt(Ticket::getId))
                .toList();
    }

    public Ticket get(int id) {
        Ticket ticket = tickets.get(id);
        if (ticket == null) {
            throw new TicketNotFoundException(id);
        }
        return ticket;
    }

    public Ticket assign(int id, String assignee) {
        Ticket ticket = get(id);
        ticket.setAssignee(assignee);
        return ticket;
    }

    public Ticket changeStatus(int id, Status next) {
        Ticket ticket = get(id);
        if (!ticket.getStatus().canMoveTo(next)) {
            throw new InvalidTransitionException(ticket.getStatus(), next);
        }
        ticket.setStatus(next);
        return ticket;
    }

    public Map<String, Object> summary() {
        Map<String, Integer> byStatus = new LinkedHashMap<>();
        for (Status status : Status.values()) {
            byStatus.put(status.label(), 0);
        }
        Map<String, Integer> byPriority = new LinkedHashMap<>();
        for (Priority priority : Priority.values()) {
            byPriority.put(priority.label(), 0);
        }
        for (Ticket ticket : tickets.values()) {
            byStatus.merge(ticket.getStatus().label(), 1, Integer::sum);
            byPriority.merge(ticket.getPriority().label(), 1, Integer::sum);
        }
        Map<String, Object> report = new LinkedHashMap<>();
        report.put("total", tickets.size());
        report.put("by_status", byStatus);
        report.put("by_priority", byPriority);
        return report;
    }

    /** Remove all tickets. Used by the tests. */
    public void reset() {
        tickets.clear();
        ids.set(0);
    }
}
