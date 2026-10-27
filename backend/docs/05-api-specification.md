# API Specification

Base URL when running locally: `http://127.0.0.1:8000`
Interactive documentation: `http://127.0.0.1:8000/docs`

| Method | Path | Purpose | Success | Errors |
|---|---|---|---|---|
| POST | /tickets | Create a ticket | 201 | 422 invalid input |
| GET | /tickets | List tickets (filters: `status`, `priority`) | 200 | 422 invalid filter |
| GET | /tickets/{id} | Get one ticket | 200 | 404 not found |
| PATCH | /tickets/{id}/assignee | Assign a ticket | 200 | 404, 422 |
| PATCH | /tickets/{id}/status | Change status | 200 | 404, 409 not allowed, 422 |
| GET | /reports/summary | Counts by status and priority | 200 | none |

## Example: create a ticket
Request:
```json
{
  "title": "Laptop will not start",
  "description": "The laptop shows a black screen after the logo.",
  "priority": "high",
  "requester_email": "employee@example.com"
}
```
Response (201):
```json
{
  "id": 1,
  "title": "Laptop will not start",
  "description": "The laptop shows a black screen after the logo.",
  "priority": "high",
  "requester_email": "employee@example.com",
  "status": "open",
  "assignee": null,
  "sla_hours": 4,
  "created_at": "2026-01-01T09:00:00+00:00"
}
```

## Example: invalid status change (409)
Request: `PATCH /tickets/1/status` with `{"status": "closed"}` on an open ticket.
Response: `{"detail": "Cannot change status from 'open' to 'closed'"}`
