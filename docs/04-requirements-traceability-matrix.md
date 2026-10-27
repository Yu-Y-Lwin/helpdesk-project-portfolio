# Requirements Traceability Matrix

This table links each requirement to a user story, an API endpoint, an automated test and a UAT scenario.

| Requirement | User story | API endpoint | Automated tests | UAT scenario |
|---|---|---|---|---|
| FR-01 Create a ticket | US-01 | POST /tickets | TC-01 | UAT-01 |
| FR-02 Validate input | US-02 | POST /tickets | TC-03, TC-04, TC-05, TC-06 | UAT-02 |
| FR-03 SLA by priority | US-01 | POST /tickets | TC-02 | UAT-01 |
| FR-04 List and filter | US-03, US-04 | GET /tickets | TC-07, TC-08, TC-09 | UAT-03 |
| FR-05 View one ticket | US-05 | GET /tickets/{id} | TC-10, TC-11 | UAT-04 |
| FR-06 Assign a ticket | US-06 | PATCH /tickets/{id}/assignee | TC-12, TC-13, TC-13b | UAT-05 |
| FR-07 Update status | US-07 | PATCH /tickets/{id}/status | TC-14, TC-17 | UAT-06 |
| FR-08 Reject invalid changes | US-07 | PATCH /tickets/{id}/status | TC-15, TC-16 | UAT-06 |
| FR-09 Summary report | US-08 | GET /reports/summary | TC-18 | UAT-07 |

Coverage check: all 9 functional requirements have at least one automated test and one UAT scenario.

## Frontend checks (Vue, Vitest)

| Requirement | User story | Component or file | Automated tests |
|---|---|---|---|
| FR-02 Validate input | US-02 | `rules.js`, `TicketForm.vue` | `rules.test.js`, `TicketForm.test.js` |
| FR-07, FR-08 Status workflow | US-07 | `rules.js`, `TicketDetail.vue` | `rules.test.js` |
| FR-01 Create a ticket | US-01 | `TicketForm.vue` | `TicketForm.test.js` |
