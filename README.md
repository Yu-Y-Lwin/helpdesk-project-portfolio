# CampusDesk Helpdesk (Java): Business Analysis and API Testing Portfolio Project

A small practice project that shows a business analysis workflow from a business problem to tested software. The company and system are **made up**. The work is a portfolio exercise, not a real project.

**Tech:** Java 17, Spring Boot 3.3, Maven, JUnit 5 and MockMvc (API tests), Postman, and a **Vue 3** frontend (Vite, Vitest).

## What this project shows

| Business analysis deliverable | File |
|---|---|
| Business requirements (problem, goals, scope, stakeholders, rules, requirements) | [docs/01-business-requirements.md](docs/01-business-requirements.md) |
| User stories with acceptance criteria and MoSCoW priority | [docs/02-user-stories.md](docs/02-user-stories.md) |
| Process flows (as-is, to-be and status workflow) | [docs/03-process-flows.md](docs/03-process-flows.md) |
| Requirements traceability matrix | [docs/04-requirements-traceability-matrix.md](docs/04-requirements-traceability-matrix.md) |
| API specification | [docs/05-api-specification.md](docs/05-api-specification.md) |
| UAT scenarios | [docs/06-uat-test-cases.md](docs/06-uat-test-cases.md) |

| Technical deliverable | Where |
|---|---|
| A small working REST API | `backend/src/main/java/com/campusdesk/helpdesk/` |
| Automated API tests (JUnit 5 and MockMvc), each mapped to a user story | `backend/src/test/java/com/campusdesk/helpdesk/TicketApiTest.java` |
| Postman collection with tests | `postman/` |
| Vue 3 web page (create, filter, assign, change status, summary) | `frontend/` |
| Frontend tests (Vitest and Vue Test Utils) | `frontend/src/__tests__/` |

## Project structure
```
helpdesk-project-portfolio/
├── README.md
├── .gitignore
├── docs/                      (six business analysis documents)
├── postman/
│   └── CampusDesk.postman_collection.json
├── backend/
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/campusdesk/helpdesk/
│       ├── main/resources/application.properties
│       └── test/java/com/campusdesk/helpdesk/TicketApiTest.java
└── frontend/
    ├── package.json
    ├── vite.config.js
    ├── index.html
    └── src/ (components, API wrapper, rules, tests)
```

## Run it

You need **Java 17 or newer** and **Maven**. Check them:

```bash
java -version
mvn -version
```

Run the automated tests:

```bash
cd backend
mvn test
```

Start the API:

```bash
mvn spring-boot:run
```
Then open http://127.0.0.1:8000/docs to see the interactive documentation (Swagger). Data is kept in memory, so it resets when the service restarts. Stop the API with Control and C.

## Run the Vue frontend

You need **Node.js 18 or newer** (`node -v`). Keep the API running on port 8000, then open a second terminal:

```bash
cd frontend
npm install
npm run dev
```
Open http://localhost:5173. The page calls `/api/...`, and Vite forwards those calls to the API, so no CORS setup is needed.

Run the frontend tests:

```bash
npm test
```

What the page does, by user story:

| Story | In the page |
|---|---|
| US-01, US-02 | Form with checks and clear error messages |
| US-03, US-04 | Ticket list with Status and Priority filters |
| US-05 | Click a row to see the details |
| US-06 | Assign a ticket to an agent email |
| US-07 | Buttons show only the allowed next statuses |
| US-08 | Summary cards at the top |

## Run the Postman tests
1. Start the API (command above).
2. In Postman, choose **Import** and select `postman/CampusDesk.postman_collection.json`.
3. Click the collection, then **Run**.

## About this project
- I wrote the requirements, stories, flows and test cases as a business analyst would, for a fictional company.
- I used AI tools to help draft the documents and code, and I reviewed and tested them myself.
- The API is a simple stand-in so the requirements can be tested. It has no login and no database.
