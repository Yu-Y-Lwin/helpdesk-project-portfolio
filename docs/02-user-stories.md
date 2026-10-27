# User Stories and Acceptance Criteria

Priority uses MoSCoW: **M**ust, **S**hould, **C**ould, **W**on't (this version).

## US-01 Create a ticket (Must) | FR-01, FR-03
**As an** employee, **I want to** report an IT problem with a title, a description and a priority, **so that** IT can fix it in the right order.

Acceptance criteria:
- Given I send a valid ticket, when I submit it, then the ticket is saved with status "open" and an ID.
- Given I choose high, medium or low priority, when the ticket is saved, then it shows the SLA of 4, 24 or 72 hours.
- Given a ticket is saved, then it has no assignee yet.

## US-02 Clear errors for bad input (Must) | FR-02
**As an** employee, **I want to** see a clear error when my ticket is wrong, **so that** I can fix it myself.

Acceptance criteria:
- Given the title is missing, when I submit, then I get an error and no ticket is created.
- Given the priority is not low, medium or high, when I submit, then I get an error.
- Given the email is not valid, when I submit, then I get an error.
- Given the description is shorter than 10 characters, when I submit, then I get an error.

## US-03 See the list of tickets (Must) | FR-04
**As an** IT agent, **I want to** see all tickets, **so that** I know what needs attention.

Acceptance criteria:
- Given tickets exist, when I open the list, then I see every ticket.
- Given I filter by status, when the list loads, then I see only tickets with that status.

## US-04 Filter by priority (Should) | FR-04
**As an** IT agent, **I want to** filter tickets by priority, **so that** I handle urgent ones first.

Acceptance criteria:
- Given I filter by "high", when the list loads, then I see only high-priority tickets.

## US-05 Open one ticket (Must) | FR-05
**As an** IT agent, **I want to** open one ticket, **so that** I can read its details.

Acceptance criteria:
- Given a ticket exists, when I open it by ID, then I see all its details.
- Given the ID does not exist, when I open it, then I get a "not found" message.

## US-06 Assign a ticket (Must) | FR-06
**As an** IT manager, **I want to** assign a ticket to an agent, **so that** someone is responsible for it.

Acceptance criteria:
- Given a ticket exists, when I assign a valid agent email, then the ticket shows that assignee.
- Given the agent email is not valid, when I assign it, then I get an error.
- Given the ticket does not exist, when I assign it, then I get a "not found" message.

## US-07 Move a ticket through its workflow (Must) | FR-07, FR-08
**As an** IT agent, **I want to** update the status of a ticket, **so that** everyone sees its progress.

Acceptance criteria:
- Given a ticket is open, when I set it to in progress, then resolved, then closed, each change is accepted.
- Given a ticket is open, when I try to close it directly, then I get a conflict error.
- Given a ticket is closed, when I try to change it, then I get a conflict error.
- Given a ticket is resolved, when I reopen it, then its status is open.

## US-08 See a summary (Should) | FR-09
**As an** IT manager, **I want to** see ticket counts by status and priority, **so that** I can plan the team's work.

Acceptance criteria:
- Given tickets exist, when I open the summary, then I see the total and the counts by status and by priority.

## Not in this version (Won't)
- US-09: Email the requester when the status changes.
- US-10: Attach a screenshot to a ticket.
- US-11: Log in with a company account.
