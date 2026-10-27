# Business Requirements Document: CampusDesk IT Helpdesk

| Item | Detail |
|---|---|
| Project | CampusDesk IT Helpdesk (a fictional company, for portfolio practice) |
| Version | 1.0 (draft) |
| Author | Business analysis exercise |
| Sponsor (fictional) | Head of IT |

## 1. Background
CampusDesk Ltd is a fictional company with about 120 employees. Employees report IT problems by email to a shared inbox. IT agents read the inbox and copy each request into a spreadsheet by hand.

## 2. Problem statement
- Requests are sometimes lost or answered late, because nothing shows what is new.
- Urgent problems look the same as small ones, so they are not handled first.
- There is no deadline for each request, so late work is not visible.
- The IT manager cannot see how much work is open without asking the agents.

## 3. Business goals and success measures
The numbers below are examples for this exercise.

| Goal | Success measure |
|---|---|
| G1. Log every IT request in one place | 100% of requests are recorded as tickets |
| G2. Handle urgent problems first | High-priority tickets get a 4-hour target |
| G3. Give the manager a live view of workload | A summary of open tickets is available at any time |
| G4. Stop requests being lost | No request is outside the system |

## 4. Scope
**In scope (version 1):**
- Create, view, filter and assign tickets
- Move a ticket through a fixed status workflow
- A summary report for the manager
- A documented API
- A simple web page to report and track tickets (Vue)

**Out of scope (version 1):**
- Email notifications
- File attachments
- User login and roles

## 5. Stakeholders
| Stakeholder | Role | Interest |
|---|---|---|
| Employee | Requester | Report a problem quickly and clearly |
| IT agent | Resolver | See the right work in the right order |
| IT manager | Decision maker | See workload and late tickets |
| Head of IT | Sponsor | Fewer lost requests, better service |

## 6. Assumptions and constraints
- All users have a company email address.
- Version 1 stores data in memory only, so data is lost when the service restarts. This is acceptable for a practice system.
- Time zone and language: English, one time zone.

## 7. Business rules
| ID | Rule |
|---|---|
| BR-01 | The target resolution time (SLA) is 4 hours for high, 24 hours for medium and 72 hours for low priority. |
| BR-02 | A ticket moves only along this path: open, in progress, resolved, closed. A resolved ticket can also be reopened. Closed tickets cannot change. |
| BR-03 | Every ticket needs a title (3 to 100 characters), a description (10 to 1000 characters), a priority and a valid requester email. |

## 8. Functional requirements
| ID | Requirement | Priority (MoSCoW) |
|---|---|---|
| FR-01 | The system shall let an employee create a ticket. | Must |
| FR-02 | The system shall check ticket input against BR-03 and return a clear error when it is invalid. | Must |
| FR-03 | The system shall set the SLA target from the priority (BR-01). | Must |
| FR-04 | The system shall list tickets and filter them by status and priority. | Must |
| FR-05 | The system shall show one ticket by its ID. | Must |
| FR-06 | The system shall let a manager assign a ticket to an agent. | Must |
| FR-07 | The system shall let an agent change the status of a ticket. | Must |
| FR-08 | The system shall reject a status change that BR-02 does not allow. | Must |
| FR-09 | The system shall give a summary of tickets by status and priority. | Should |

## 9. Non-functional requirements
| ID | Requirement |
|---|---|
| NFR-01 | Normal requests respond in under 2 seconds. |
| NFR-02 | Error messages say what is wrong in plain language. |
| NFR-03 | The API is documented and can be tried from a web page. |
| NFR-04 | Every requirement is covered by at least one automated test. |

## 10. Risks and dependencies
| Risk | Effect | Response |
|---|---|---|
| Data is lost on restart | Tickets disappear | Accept for version 1; add a database in version 2 |
| No login | Anyone could assign or close tickets | Add roles in version 2 |
| Agents keep using the old inbox | Tickets are missed | Agree a cut-over date and tell staff |

## 11. Open questions (for the sponsor)
1. Should high-priority tickets send an alert to the manager?
2. Who may reopen a resolved ticket: the requester only, or also the agent?
3. How long should closed tickets be kept?

## My Notes
**MoSCoW prioritisation** \
MoSCoW is a prioritisation technique for helping to understand and manage priorities. The letters stand for:
* Must Have
* Should Have
* Could Have
* Won’t Have this time
