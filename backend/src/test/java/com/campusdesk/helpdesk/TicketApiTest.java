package com.campusdesk.helpdesk;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

/**
 * API tests for the CampusDesk Helpdesk API.
 *
 * Each test method name starts with its test case ID (TC-xx) so it can be traced to a user story
 * (US-xx) and a requirement (FR-xx) in docs/04-requirements-traceability-matrix.md.
 */
@SpringBootTest
@AutoConfigureMockMvc
class TicketApiTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TicketService service;

    @BeforeEach
    void cleanData() {
        service.reset();
    }

    // ---------- helpers ----------

    private Map<String, Object> validTicket() {
        Map<String, Object> ticket = new LinkedHashMap<>();
        ticket.put("title", "Laptop will not start");
        ticket.put("description", "The laptop shows a black screen after the logo.");
        ticket.put("priority", "high");
        ticket.put("requester_email", "employee@example.com");
        return ticket;
    }

    private ResultActions postTicket(Map<String, Object> body) throws Exception {
        return mvc.perform(post("/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)));
    }

    private ResultActions sendPatch(String path, Map<String, Object> body) throws Exception {
        return mvc.perform(patch(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)));
    }

    private int createTicket(String priority) throws Exception {
        Map<String, Object> body = validTicket();
        body.put("priority", priority);
        String json = postTicket(body)
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Integer id = JsonPath.read(json, "$.id");
        return id;
    }

    private void setStatus(int id, String newStatus) throws Exception {
        sendPatch("/tickets/" + id + "/status", Map.of("status", newStatus)).andExpect(status().isOk());
    }

    // ---------- US-01 Create a ticket (FR-01, FR-03) ----------

    @Test
    void tc01_createValidTicketReturns201AndOpenStatus() throws Exception {
        postTicket(validTicket())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("open"))
                .andExpect(jsonPath("$.assignee").value(nullValue()))
                .andExpect(jsonPath("$.title").value("Laptop will not start"));
    }

    @ParameterizedTest
    @CsvSource({"low,72", "medium,24", "high,4"})
    void tc02_slaHoursDependOnPriority(String priority, int expectedSla) throws Exception {
        Map<String, Object> body = validTicket();
        body.put("priority", priority);

        postTicket(body)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sla_hours").value(expectedSla));
    }

    // ---------- US-02 Clear errors for invalid input (FR-02) ----------

    @Test
    void tc03_missingTitleReturns422() throws Exception {
        Map<String, Object> body = validTicket();
        body.remove("title");

        postTicket(body).andExpect(status().isUnprocessableEntity());
    }

    @Test
    void tc04_invalidPriorityReturns422() throws Exception {
        Map<String, Object> body = validTicket();
        body.put("priority", "urgent");

        postTicket(body).andExpect(status().isUnprocessableEntity());
    }

    @Test
    void tc05_invalidEmailReturns422() throws Exception {
        Map<String, Object> body = validTicket();
        body.put("requester_email", "not-an-email");

        postTicket(body).andExpect(status().isUnprocessableEntity());
    }

    @Test
    void tc06_descriptionTooShortReturns422() throws Exception {
        Map<String, Object> body = validTicket();
        body.put("description", "Too short");

        postTicket(body).andExpect(status().isUnprocessableEntity());
    }

    // ---------- US-03 and US-04 List and filter tickets (FR-04) ----------

    @Test
    void tc07_listReturnsAllCreatedTickets() throws Exception {
        createTicket("high");
        createTicket("low");

        mvc.perform(get("/tickets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void tc08_filterByPriorityReturnsOnlyThatPriority() throws Exception {
        createTicket("high");
        createTicket("low");
        createTicket("low");

        mvc.perform(get("/tickets").param("priority", "low"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].priority").value("low"))
                .andExpect(jsonPath("$[1].priority").value("low"));
    }

    @Test
    void tc09_filterByStatusReturnsOnlyThatStatus() throws Exception {
        int first = createTicket("high");
        createTicket("high");
        setStatus(first, "in_progress");

        mvc.perform(get("/tickets").param("status", "in_progress"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(first));
    }

    // ---------- US-05 Open one ticket (FR-05) ----------

    @Test
    void tc10_getTicketById() throws Exception {
        int id = createTicket("high");

        mvc.perform(get("/tickets/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void tc11_getMissingTicketReturns404() throws Exception {
        mvc.perform(get("/tickets/999")).andExpect(status().isNotFound());
    }

    // ---------- US-06 Assign a ticket (FR-06) ----------

    @Test
    void tc12_assignTicketToAgent() throws Exception {
        int id = createTicket("high");

        sendPatch("/tickets/" + id + "/assignee", Map.of("assignee", "agent@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assignee").value("agent@example.com"));
    }

    @Test
    void tc13_assignWithInvalidEmailReturns422() throws Exception {
        int id = createTicket("high");

        sendPatch("/tickets/" + id + "/assignee", Map.of("assignee", "agent"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void tc13b_assignMissingTicketReturns404() throws Exception {
        sendPatch("/tickets/999/assignee", Map.of("assignee", "agent@example.com"))
                .andExpect(status().isNotFound());
    }

    // ---------- US-07 Status workflow (FR-07, FR-08) ----------

    @Test
    void tc14_fullStatusWorkflow() throws Exception {
        int id = createTicket("high");

        for (String next : new String[] {"in_progress", "resolved", "closed"}) {
            sendPatch("/tickets/" + id + "/status", Map.of("status", next))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(next));
        }
    }

    @Test
    void tc15_openCannotJumpToClosed() throws Exception {
        int id = createTicket("high");

        sendPatch("/tickets/" + id + "/status", Map.of("status", "closed"))
                .andExpect(status().isConflict());
    }

    @Test
    void tc16_closedTicketCannotChange() throws Exception {
        int id = createTicket("high");
        setStatus(id, "in_progress");
        setStatus(id, "resolved");
        setStatus(id, "closed");

        sendPatch("/tickets/" + id + "/status", Map.of("status", "open"))
                .andExpect(status().isConflict());
    }

    @Test
    void tc17_resolvedTicketCanBeReopened() throws Exception {
        int id = createTicket("high");
        setStatus(id, "in_progress");
        setStatus(id, "resolved");

        sendPatch("/tickets/" + id + "/status", Map.of("status", "open"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("open"));
    }

    // ---------- US-08 Summary report (FR-09) ----------

    @Test
    void tc18_summaryCountsByStatusAndPriority() throws Exception {
        int first = createTicket("high");
        createTicket("low");
        setStatus(first, "in_progress");

        mvc.perform(get("/reports/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(2))
                .andExpect(jsonPath("$.by_status.open").value(1))
                .andExpect(jsonPath("$.by_status.in_progress").value(1))
                .andExpect(jsonPath("$.by_priority.high").value(1))
                .andExpect(jsonPath("$.by_priority.low").value(1));
    }
}
