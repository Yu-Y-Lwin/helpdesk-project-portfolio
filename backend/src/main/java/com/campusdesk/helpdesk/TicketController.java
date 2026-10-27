package com.campusdesk.helpdesk;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController {

    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }

    @PostMapping("/tickets")
    @ResponseStatus(HttpStatus.CREATED)
    public Ticket create(@Valid @RequestBody CreateTicketRequest request) {
        return service.create(request);
    }

    @GetMapping("/tickets")
    public List<Ticket> list(
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "priority", required = false) String priority) {
        Status statusFilter = status == null ? null : Status.parse(status);
        Priority priorityFilter = priority == null ? null : Priority.parse(priority);
        return service.list(statusFilter, priorityFilter);
    }

    @GetMapping("/tickets/{id}")
    public Ticket get(@PathVariable("id") int id) {
        return service.get(id);
    }

    @PatchMapping("/tickets/{id}/assignee")
    public Ticket assign(@PathVariable("id") int id, @Valid @RequestBody AssigneeUpdateRequest request) {
        return service.assign(id, request.assignee());
    }

    @PatchMapping("/tickets/{id}/status")
    public Ticket changeStatus(@PathVariable("id") int id, @Valid @RequestBody StatusUpdateRequest request) {
        return service.changeStatus(id, request.status());
    }

    @GetMapping("/reports/summary")
    public Map<String, Object> summary() {
        return service.summary();
    }
}
