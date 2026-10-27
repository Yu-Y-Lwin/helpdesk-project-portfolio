# User Acceptance Test (UAT) Scenarios

These scenarios are written for a business user. Run them in the web page (`http://localhost:5173`) or in the interactive API documentation at `http://127.0.0.1:8000/docs`.

| ID | Story | Steps | Expected result |
|---|---|---|---|
| UAT-01 | US-01 | Create a ticket with priority "high". | The ticket is created as "open" with an ID and an SLA of 4 hours. |
| UAT-02 | US-02 | Create a ticket with no title. | An error is shown and no ticket is created. |
| UAT-03 | US-03, US-04 | Create three tickets with different priorities, then list with priority "low". | Only the low-priority tickets are shown. |
| UAT-04 | US-05 | Open ticket 1, then open ticket 999. | Ticket 1 shows its details. Ticket 999 shows "not found". |
| UAT-05 | US-06 | Assign ticket 1 to agent@example.com. | The ticket shows that assignee. |
| UAT-06 | US-07 | Move ticket 1 to in progress, resolved, closed. Then try to reopen it. | Each step is accepted, and the final reopen is refused. |
| UAT-07 | US-08 | Open the summary. | Totals and counts by status and priority are correct. |

Result log (fill in after you run them):

| ID | Date | Result (pass or fail) | Notes |
|---|---|---|---|
| UAT-01 | | | |
| UAT-02 | | | |
| UAT-03 | | | |
| UAT-04 | | | |
| UAT-05 | | | |
| UAT-06 | | | |
| UAT-07 | | | |
